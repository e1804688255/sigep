package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.ITimbradaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia;
import com.sifuturo.sigep.dominio.entidades.enums.TipoTimbrada;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ISolicitudAusenciaRepositorio; // <--- NUEVO IMPORT
import com.sifuturo.sigep.dominio.repositorios.ITimbradaRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;
import com.sifuturo.sigep.presentacion.dto.response.ReporteAsistenciaConsolidadoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimbradaUseCaseImpl implements ITimbradaUseCase {

	private final ITimbradaRepositorio timbradaRepositorio;
	private final IEmpleadoRepositorio empleadoRepositorio;
	private final IUsuarioRepositorio usuarioRepositorio;
	private final ISolicitudAusenciaRepositorio ausenciaRepositorio;

	private static final LocalTime HORARIO_ENTRADA = LocalTime.of(8, 0);
	private static final LocalTime HORARIO_SALIDA = LocalTime.of(16, 30);

	@Override
	@Transactional(readOnly = true)
	public List<Timbrada> listarReporteGeneral(LocalDateTime inicio, LocalDateTime fin, Long areaId) {
		if (inicio == null)
			inicio = LocalDateTime.now().withHour(0).withMinute(0);
		if (fin == null)
			fin = LocalDateTime.now().withHour(23).withMinute(59);
		return timbradaRepositorio.listarTodasEnRango(inicio, fin, areaId);
	}

	@Override
	public List<Timbrada> listarMisTimbradasConFiltro(Long idEmpleado, LocalDateTime inicio, LocalDateTime fin) {
		if (inicio == null || fin == null) {
			inicio = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
			fin = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
		}
		return timbradaRepositorio.listarPorEmpleadoYRango(idEmpleado, inicio, fin);
	}

	@Override
	@Transactional
	public Timbrada registrar(RegistrarTimbradaDto dto) {
		Empleado empleado = empleadoRepositorio.buscarPorId(dto.getIdEmpleado())
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

		Timbrada ultimaTimbrada = timbradaRepositorio.buscarUltimaPorEmpleado(dto.getIdEmpleado());

		// 1. Validar secuencia (Entrada -> Almuerzo -> Salida)
		validarReglasDeNegocio(ultimaTimbrada, dto.getTipo());

		Timbrada nuevaTimbrada = Timbrada.builder().empleado(empleado).fechaHora(LocalDateTime.now())
				.tipo(dto.getTipo()).ipOrigen(dto.getIpOrigen()).observacion(dto.getObservacion()).build();

		Usuario usuario = usuarioRepositorio.listarTodos().stream()
				.filter(u -> u.getEmpleado() != null && u.getEmpleado().getIdEmpleado().equals(dto.getIdEmpleado()))
				.findFirst().orElse(null);
		validarIp(usuario, dto.getIpOrigen(), nuevaTimbrada);
		nuevaTimbrada.setEstado(true);
		return timbradaRepositorio.guardar(nuevaTimbrada);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Timbrada> listarMisTimbradas(Long idEmpleado) {
		return timbradaRepositorio.listarPorEmpleado(idEmpleado);
	}

	private void validarReglasDeNegocio(Timbrada ultima, TipoTimbrada nuevoTipo) {
		LocalDate hoy = LocalDate.now();

		// 1. Si no hay registros previos o el último fue un día anterior
		if (ultima == null || !ultima.getFechaHora().toLocalDate().equals(hoy)) {
			if (nuevoTipo != TipoTimbrada.ENTRADA) {
				throw new ReglaNegocioException("El primer registro del día debe ser una ENTRADA.");
			}
			return; // Si es ENTRADA y es un nuevo día, todo está correcto, salimos.
		}

		// 2. Si llegamos aquí, significa que SÍ hay registros el día de HOY.
		TipoTimbrada ultimoTipo = ultima.getTipo();

		if (nuevoTipo == TipoTimbrada.ENTRADA) {
			throw new ReglaNegocioException("Ya registraste tu ENTRADA el día de hoy.");
		}

		// Validamos la secuencia basada en el último registro de HOY
		switch (ultimoTipo) {
		case ENTRADA:
			if (nuevoTipo != TipoTimbrada.ALMUERZO_INICIO && nuevoTipo != TipoTimbrada.SALIDA) {
				throw new ReglaNegocioException("Después de la ENTRADA debes registrar INICIO DE ALMUERZO o SALIDA.");
			}
			break;
		case ALMUERZO_INICIO:
			if (nuevoTipo != TipoTimbrada.ALMUERZO_FIN) {
				throw new ReglaNegocioException("Estás en hora de almuerzo. Debes registrar FIN DE ALMUERZO.");
			}
			break;
		case ALMUERZO_FIN:
			if (nuevoTipo != TipoTimbrada.SALIDA) {
				throw new ReglaNegocioException("Ya regresaste del almuerzo. El siguiente registro debe ser SALIDA.");
			}
			break;
		case SALIDA:
			// Si el último registro de HOY fue salida, ya no puede hacer más nada hoy.
			throw new ReglaNegocioException("Ya registraste tu SALIDA de jornada hoy. Hasta mañana.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReporteAsistenciaConsolidadoDto> generarReporteCalculado(LocalDateTime inicio, LocalDateTime fin,
			Long areaId) {
		// 1. Obtener datos base
		List<Timbrada> timbradas = listarReporteGeneral(inicio, fin, areaId);
		List<Empleado> empleados = empleadoRepositorio.listarTodos(); // Necesitamos a todos para detectar faltas

		// Si se filtró por área, filtramos la lista de empleados también
		if (areaId != null) {
			empleados = empleados.stream().filter(e -> e.getArea() != null && e.getArea().getId().equals(areaId))
					.collect(Collectors.toList());
		}

		List<SolicitudAusencia> ausenciasAprobadas = ausenciaRepositorio.listarTodas().stream()
				.filter(s -> s.getEstadoSolicitud() == EstadoSolicitud.APROBADO).collect(Collectors.toList());

		// 2. Agrupar timbradas existentes por Empleado y Fecha para búsqueda rápida
		var timbradasAgrupadas = timbradas.stream().collect(Collectors.groupingBy(t -> t.getEmpleado().getIdEmpleado(),
				Collectors.groupingBy(t -> t.getFechaHora().toLocalDate())));

		List<ReporteAsistenciaConsolidadoDto> reporteCompleto = new ArrayList<>();

		// 3. GENERAR EL RANGO DE FECHAS
		LocalDate fechaInicio = inicio.toLocalDate();
		LocalDate fechaFin = fin.toLocalDate();

		for (Empleado emp : empleados) {
			// Iteramos cada día del rango para cada empleado
			for (LocalDate date = fechaInicio; !date.isAfter(fechaFin); date = date.plusDays(1)) {

				// Omitir fines de semana si tu empresa no labora Sáb/Dom
				// if (date.getDayOfWeek().getValue() >= 6)
				// continue;

				List<Timbrada> registrosDelDia = timbradasAgrupadas.getOrDefault(emp.getIdEmpleado(), Map.of())
						.getOrDefault(date, new ArrayList<>());

				// Procesar los datos (tu lógica actual encapsulada)
				reporteCompleto.add(procesarDiaEmpleado(emp, date, registrosDelDia, ausenciasAprobadas));
			}
		}

		reporteCompleto.sort((a, b) -> b.getFecha().compareTo(a.getFecha()));
		return reporteCompleto;
	}

	// Método auxiliar para no ensuciar el bucle principal
	private ReporteAsistenciaConsolidadoDto procesarDiaEmpleado(Empleado empleado, LocalDate fecha,
			List<Timbrada> listaTimbradas, List<SolicitudAusencia> ausencias) {

		Timbrada entrada = listaTimbradas.stream().filter(t -> t.getTipo() == TipoTimbrada.ENTRADA).findFirst()
				.orElse(null);
		Timbrada salida = listaTimbradas.stream().filter(t -> t.getTipo() == TipoTimbrada.SALIDA).reduce((f, s) -> s)
				.orElse(null);
		Timbrada almInicio = listaTimbradas.stream().filter(t -> t.getTipo() == TipoTimbrada.ALMUERZO_INICIO)
				.findFirst().orElse(null);
		Timbrada almFin = listaTimbradas.stream().filter(t -> t.getTipo() == TipoTimbrada.ALMUERZO_FIN).findFirst()
				.orElse(null);

		LocalTime horaEntradaReal = (entrada != null) ? entrada.getFechaHora().toLocalTime() : null;
		LocalTime horaSalidaReal = (salida != null) ? salida.getFechaHora().toLocalTime() : null;

		long minAtrasoEntrada = 0;
		long minExcesoAlmuerzo = 0;
		String tiempoExtra = "0h 0m";

		if (entrada != null && horaEntradaReal.isAfter(HORARIO_ENTRADA)) {
			minAtrasoEntrada = Duration.between(HORARIO_ENTRADA, horaEntradaReal).toMinutes();
		}

		if (almInicio != null && almFin != null) {
			long duracionAlm = Duration.between(almInicio.getFechaHora(), almFin.getFechaHora()).toMinutes();
			if (duracionAlm > 30)
				minExcesoAlmuerzo = duracionAlm - 30;
		}

		if (salida != null && horaSalidaReal.isAfter(HORARIO_SALIDA)) {
			Duration extra = Duration.between(HORARIO_SALIDA, horaSalidaReal);
			tiempoExtra = String.format("%dh %dm", extra.toHours(), extra.toMinutesPart());
		}

		// Lógica de Estado
		String estado = (entrada == null) ? "FALTA" : (minAtrasoEntrada + minExcesoAlmuerzo > 0) ? "ATRASO" : "PUNTUAL";

		// Cruzar con Ausencias
		SolicitudAusencia ausencia = buscarAusenciaParaFecha(ausencias, empleado.getIdEmpleado(), fecha);
		if (ausencia != null) {
			if (ausencia.getTipo() == TipoAusencia.CITA_MEDICA) {
				estado = "JUSTIFICADO";
				minAtrasoEntrada = 0;
				minExcesoAlmuerzo = 0;
			} else {
				estado = ausencia.getTipo().name();
				minAtrasoEntrada = 0;
				minExcesoAlmuerzo = 0;
			}
		}

		return ReporteAsistenciaConsolidadoDto.builder().idEmpleado(empleado.getIdEmpleado())
				.nombreEmpleado(empleado.getPersona().getNombres() + " " + empleado.getPersona().getApellidos())
				.area(empleado.getArea() != null ? empleado.getArea().getNombre() : "N/A").fecha(fecha)
				.horaEntrada(horaEntradaReal).horaSalida(horaSalidaReal)
				.minutosAtraso(minAtrasoEntrada + minExcesoAlmuerzo).minutosAtrasoEntrada(minAtrasoEntrada)
				.minutosExcesoAlmuerzo(minExcesoAlmuerzo).tiempoExtra(tiempoExtra)
				.esFalta(entrada == null && ausencia == null).estadoAsistencia(estado).build();
	}

	// Método auxiliar para buscar en la lista de ausencias
	private SolicitudAusencia buscarAusenciaParaFecha(List<SolicitudAusencia> ausencias, Long idEmpleado,
			LocalDate fechaReporte) {
		return ausencias.stream().filter(a -> a.getEmpleado().getIdEmpleado().equals(idEmpleado)) // Coincide empleado
				.filter(a -> {
					LocalDate inicio = a.getFechaInicio().toLocalDate();
					LocalDate fin = a.getFechaFin().toLocalDate();
					// Verificamos si la fecha del reporte cae dentro del rango de la solicitud
					return (fechaReporte.isEqual(inicio) || fechaReporte.isAfter(inicio))
							&& (fechaReporte.isEqual(fin) || fechaReporte.isBefore(fin));
				}).findFirst().orElse(null);
	}

	private void validarIp(Usuario usuario, String ipOrigen, Timbrada timbrada) {
		if (ipOrigen == null || ipOrigen.isEmpty()) {
			timbrada.setObservacion("ADVERTENCIA: No se pudo detectar la IP.");
			return;
		}

		// Si el usuario tiene una IP configurada, validamos que coincida
		if (usuario != null && usuario.getIpPermitida() != null && !usuario.getIpPermitida().isEmpty()) {
			if (!usuario.getIpPermitida().equals(ipOrigen)) {
				String mensaje = String.format("Intento de timbrada desde IP no autorizada: %s", ipOrigen);
				timbrada.setObservacion(mensaje);
				throw new ReglaNegocioException(
						"Estás intentando timbrar desde una red (IP) no autorizada. Conéctate a la red de la oficina.");
			}
		}
	}
}