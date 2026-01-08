package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException; // <--- Importar
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
		// Validación: Cédula única
		if (repositorio.obtenerPorCedula(persona.getCedula()).isPresent()) {
			// Lanzamos ReglaNegocioException -> El usuario verá un 400 Bad Request
			throw new ReglaNegocioException("La persona con cédula " + persona.getCedula() + " ya está registrada.");
		}
		return repositorio.crear(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> obtenerPorCedula(String cedula) {
		// Opción A: Retornar Optional (El controlador decide qué hacer)
		return repositorio.obtenerPorCedula(cedula);

		// Opción B (Si quisieras forzar el error aquí):
		// return Optional.of(repositorio.obtenerPorCedula(cedula)
		// .orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada
		// con cédula: " + cedula)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listar() {
		return repositorio.listar();
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		// Verificar si existe antes de eliminar
		// Nota: Necesitarías un método en el repo para buscar por ID o existsById
		// Si no existe -> throw new RecursoNoEncontradoException("ID no existe");

		repositorio.eliminar(id);
	}
}