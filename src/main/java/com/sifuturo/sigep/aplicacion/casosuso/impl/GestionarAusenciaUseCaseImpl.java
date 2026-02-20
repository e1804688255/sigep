package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IGestionarAusenciaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia; // Importante
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ISolicitudAusenciaRepositorio;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;
import com.sifuturo.sigep.presentacion.mapeadores.ISolicitudAusenciaDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GestionarAusenciaUseCaseImpl implements IGestionarAusenciaUseCase {

	private final ISolicitudAusenciaRepositorio repositorio;
	private final IEmpleadoRepositorio empleadoRepositorio; // <--- Inyecta esto
	private final ISolicitudAusenciaDtoMapper mapper;

	@Override
	public SolicitudAusenciaDTO crearSolicitud(SolicitudAusenciaDTO dto) {
		// Gracias al cambio en el MAPPER, aquí 'solicitud.getEmpleado()' ya tendrá el
		// ID correcto
		SolicitudAusencia solicitud = mapper.toDomain(dto);

		solicitud.setFechaSolicitud(LocalDateTime.now());
		solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_APROBACION_JEFE);

		// Validación Cita Médica
		if (solicitud.getTipo() == TipoAusencia.CITA_MEDICA) {
			if (solicitud.getEvidenciaBase64() == null || solicitud.getEvidenciaBase64().isEmpty()) {
				throw new ReglaNegocioException("Para Citas Médicas es obligatorio adjuntar evidencia.");
			}
		}

		return mapper.toDto(repositorio.guardar(solicitud));
	}

	@Override
	public SolicitudAusenciaDTO aprobarPorJefe(Long idSolicitud) {
		SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
				.orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

		// CORRECCIÓN: 'getEstadoSolicitud'
		if (solicitud.getEstadoSolicitud() != EstadoSolicitud.PENDIENTE_APROBACION_JEFE) {
			throw new RuntimeException("Esta solicitud no está pendiente de aprobación por el jefe.");
		}

		// Cambio de estado
		solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_AUTORIZACION_TH);

		return mapper.toDto(repositorio.guardar(solicitud));
	}

	@Override
	public SolicitudAusenciaDTO aprobarPorTH(Long idSolicitud) {
		SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
				.orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

		// CORRECCIÓN: 'getEstadoSolicitud'
		if (solicitud.getEstadoSolicitud() != EstadoSolicitud.PENDIENTE_AUTORIZACION_TH) {
			throw new RuntimeException("La solicitud debe ser aprobada por el jefe antes de llegar a Talento Humano.");
		}

		// Aprobación final
		solicitud.setEstadoSolicitud(EstadoSolicitud.APROBADO);

		// AQUÍ LÓGICA FUTURA: Descontar días si es Vacaciones
		// if (solicitud.getTipo() == TipoAusencia.VACACIONES) { ... }

		return mapper.toDto(repositorio.guardar(solicitud));
	}

	@Override
	public List<SolicitudAusenciaDTO> listarPorEmpleado(Long idEmpleado) {
		// 1. Llamar al repositorio (Dominio)
		List<SolicitudAusencia> lista = repositorio.listarPorEmpleado(idEmpleado);

		// 2. Mapear Lista de Entidades a Lista de DTOs
		// Si tu mapper tiene un método toDtoList, úsalo. Si no, usa este stream:
		return lista.stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public List<SolicitudAusenciaDTO> listarTodas() {
		return repositorio.listarTodas().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public SolicitudAusenciaDTO rechazar(Long idSolicitud, String motivoRechazo) {
		SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
				.orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

		solicitud.setEstadoSolicitud(EstadoSolicitud.RECHAZADO_TH);
		solicitud.setObservaciones(motivoRechazo); // Guardamos el motivo en el campo nuevo

		return mapper.toDto(repositorio.guardar(solicitud));
	}

	@Override
	public SolicitudAusenciaDTO procesarAprobacion(Long idSolicitud, Long idEjecutor) {
	    // 1. Buscamos la solicitud
	    SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
	            .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));

	    // 2. LÓGICA SIMPLIFICADA (Máquina de Estados)
	    // Confiamos en que si llega aquí, es porque el usuario correcto le dio clic en el Front.

	    if (solicitud.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE_APROBACION_JEFE) {
	        // Si estaba donde el Jefe -> Pasa a Talento Humano
	        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_AUTORIZACION_TH);
	    } 
	    else if (solicitud.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE_AUTORIZACION_TH) {
	        // Si estaba en Talento Humano -> Se Aprueba Final
	        solicitud.setEstadoSolicitud(EstadoSolicitud.APROBADO);
	        
	        // (Opcional) Aquí podrías descontar días de vacaciones si fuera necesario en el futuro
	    } 
	    else {
	        // Solo por seguridad para no aprobar algo que ya estaba rechazado o aprobado
	        throw new ReglaNegocioException("La solicitud no está en un estado pendiente (" + solicitud.getEstadoSolicitud() + ")");
	    }

	    return mapper.toDto(repositorio.guardar(solicitud));
	}
}