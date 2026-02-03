package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException; // <--- Importar
import com.sifuturo.sigep.aplicacion.util.AppUtil;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;

@Service
public class PersonaUseCaseImpl implements IPersonaUseCase {

	private final IPersonaRepositorio repositorio;

	public PersonaUseCaseImpl(IPersonaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	@Transactional
	public Persona crear(Persona persona) {
		if (repositorio.obtenerPorCedula(persona.getCedula()).isPresent()) {
			throw new ReglaNegocioException("La persona con cédula " + persona.getCedula() + " ya está registrada.");
		}
		return repositorio.crear(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> obtenerPorCedula(String cedula) {
		// Opción A: Retornar Optional (El controlador decide qué hacer)
		return repositorio.obtenerPorCedula(cedula);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listarTodos() {
		return repositorio.listarTodos();

	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listarActivos() {
		return repositorio.listarActivos();

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> buscarPorId(Long id) {
        return repositorio.buscarPorId(id);

	}

	@Override
	public boolean existePorCedula(String cedula) {
        return repositorio.existePorCedula(cedula);

	}

	@Override
	@Transactional
	public Persona actualizar(Long id, Persona persona) {
		Persona personaDb = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));

		AppUtil.copiarPropiedadesNoNulas(persona, personaDb);
		
		return repositorio.crear(personaDb);
	}
	
	
	@Override
	@Transactional
	public void eliminar(Long id) {
		Persona persona = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

		persona.setEstado(false);
		repositorio.crear(persona);
	}
}