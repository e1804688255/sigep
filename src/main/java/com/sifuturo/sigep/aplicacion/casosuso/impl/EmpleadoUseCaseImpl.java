package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException; 
import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;

@Service
public class EmpleadoUseCaseImpl implements IEmpleadoUseCase {

	private final IEmpleadoRepositorio empleadoRepositorio;
	private final IPersonaRepositorio personaRepositorio;

	public EmpleadoUseCaseImpl(IEmpleadoRepositorio empleadoRepositorio, IPersonaRepositorio personaRepositorio) {
		this.empleadoRepositorio = empleadoRepositorio;
		this.personaRepositorio = personaRepositorio;
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

		empleado.setPersona(personaCompleta);

		Optional<Empleado> existente = empleadoRepositorio.buscarPorCodigo(empleado.getCodigoEmpleado());
		if (existente.isPresent()) {
			if (empleado.getIdEmpleado() == null || !existente.get().getIdEmpleado().equals(empleado.getIdEmpleado())) {
				throw new ReglaNegocioException("El código ya existe.");
			}
		}

		
		return empleadoRepositorio.guardar(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Empleado> buscarPorId(Long id) {
		return empleadoRepositorio.buscarPorId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Empleado> buscarPorCodigo(String codigo) {
		return empleadoRepositorio.buscarPorCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> listarTodos() {
		return empleadoRepositorio.listarTodos();
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		empleadoRepositorio.eliminar(id);
	}
}