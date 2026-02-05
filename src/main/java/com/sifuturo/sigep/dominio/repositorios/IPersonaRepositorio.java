package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Persona;

public interface IPersonaRepositorio {
	Persona crear(Persona persona);

	Optional<Persona> obtenerPorCedula(String cedula);

	List<Persona> listarTodos();

	List<Persona> listarActivos();

	Optional<Persona> buscarPorId(Long id);

	boolean existsByCedula(String cedula);
	boolean existePorId(Long id);

}