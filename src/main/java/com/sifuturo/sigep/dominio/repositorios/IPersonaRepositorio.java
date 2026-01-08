package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Persona;

public interface IPersonaRepositorio {
	Persona crear(Persona persona);

	Optional<Persona> obtenerPorCedula(String cedula);

	List<Persona> listar();

	void eliminar(int id);
	// boolean existePorCedula(String cedula); // Opcional si quieres optimizar
}