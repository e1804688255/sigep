package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;
import com.sifuturo.sigep.aplicacion.util.AppUtil;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoPersona;
import com.sifuturo.sigep.dominio.repositorios.IAreaRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;

@Service
public class EmpleadoUseCaseImpl implements IEmpleadoUseCase {

	private final IEmpleadoRepositorio empleadoRepositorio;
	private final IPersonaRepositorio personaRepositorio;
	private final IAreaRepositorio areaRepositorio;
	private final ICargoRepositorio cargoRepositorio;

	public EmpleadoUseCaseImpl(IEmpleadoRepositorio empleadoRepositorio, IPersonaRepositorio personaRepositorio,
			IAreaRepositorio areaRepositorio, ICargoRepositorio cargoRepositorio) {
		this.empleadoRepositorio = empleadoRepositorio;
		this.personaRepositorio = personaRepositorio;
		this.areaRepositorio = areaRepositorio;
		this.cargoRepositorio = cargoRepositorio;
	}

	@Override
	@Transactional
	public Empleado guardar(Empleado empleado) {

		if (empleado.getPersona() == null || empleado.getPersona().getId() == null) {
			throw new ReglaNegocioException("Debe indicar el ID de la persona.");
		}
		Long idPersona = empleado.getPersona().getId();
		Persona personaCompleta = personaRepositorio.buscarPorId(idPersona)
				.orElseThrow(() -> new RecursoNoEncontradoException("No existe la persona con ID: " + idPersona));

		if (personaCompleta.getEstadoPersona() == EstadoPersona.EMPLEADO) {
            throw new ReglaNegocioException("Esta persona YA es un empleado activo. No se puede contratar nuevamente.");
        }
		empleado.setPersona(personaCompleta);

		if (empleado.getArea() == null || empleado.getArea().getId() == null) {
			throw new ReglaNegocioException("Debe indicar el ID del Área.");
		}
		Long idArea = empleado.getArea().getId();
		Area areaCompleta = areaRepositorio.buscarPorId(idArea)
				.orElseThrow(() -> new RecursoNoEncontradoException("No existe el Área con ID: " + idArea));
		empleado.setArea(areaCompleta); 

		if (empleado.getCargo() == null || empleado.getCargo().getId() == null) {
			throw new ReglaNegocioException("Debe indicar el ID del Cargo.");
		}
		Long idCargo = empleado.getCargo().getId();
		Cargo cargoCompleto = cargoRepositorio.buscarPorId(idCargo)
				.orElseThrow(() -> new RecursoNoEncontradoException("No existe el Cargo con ID: " + idCargo));
		empleado.setCargo(cargoCompleto); 

		Optional<Empleado> existente = empleadoRepositorio.buscarPorCodigo(empleado.getCodigoEmpleado());
		if (existente.isPresent()) {
			if (empleado.getIdEmpleado() == null || !existente.get().getIdEmpleado().equals(empleado.getIdEmpleado())) {
				throw new ReglaNegocioException(
						"El código de empleado " + empleado.getCodigoEmpleado() + " ya existe.");
			}
		}
		// 4. CAMBIO DE ESTADO: Evolucionamos a la Persona
		personaCompleta.setEstadoPersona(EstadoPersona.EMPLEADO);
        personaRepositorio.crear(personaCompleta); // Actualizamos la tabla personas

		return empleadoRepositorio.guardar(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Empleado> buscarPorId(Long id) {
		return empleadoRepositorio.buscarPorId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> listarTodos() {
		return empleadoRepositorio.listarTodos();
	}

	@Override
	@Transactional
	public Empleado actualizar(Long id, Empleado empleadoEntrada) {
		Empleado empleadoDb = empleadoRepositorio.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));

		AppUtil.copiarPropiedadesNoNulas(empleadoEntrada, empleadoDb);
		if (empleadoEntrada.getPersona() != null) {
			AppUtil.copiarPropiedadesNoNulas(empleadoEntrada.getPersona(), empleadoDb.getPersona());
		}
		return empleadoRepositorio.guardar(empleadoDb);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		Empleado empleado = empleadoRepositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("empleado no encontrada con ID: " + id));

		empleado.setEstado(false);
		empleadoRepositorio.guardar(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> listarActivos() {
        return empleadoRepositorio.listarActivos();

	}

}