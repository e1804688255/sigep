package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Persona;

public interface IPersonaUseCase {
	Persona crear(Persona persona);

	Optional<Persona> obtenerPorCedula(String cedula);

	List<Persona> listarTodos();

	List<Persona> listarActivos();

	Optional<Persona> buscarPorId(Long id);

	boolean existePorCedula(String cedula);

	Persona actualizar(Long id, Persona persona);

	void eliminar(Long id);

}
