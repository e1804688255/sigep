package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Persona;

public interface IPersonaUseCase {
	Persona crear(Persona persona);
	Optional<Persona> obtenerPorCedula(String cedula);
	List<Persona> listar();
	void eliminar(int id);
	
}
