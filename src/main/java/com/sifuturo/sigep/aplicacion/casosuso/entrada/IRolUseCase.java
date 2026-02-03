package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Rol;

public interface IRolUseCase {

	List<Rol> listarTodos();

	List<Rol> listarActivos();

	Optional<Rol> buscarPorId(Long id);

	Rol guardar(Rol area);

	boolean existePorNombre(String nombre);

	Rol actualizar(Long id, Rol areaEntrada);
	
	void eliminar(Long id);
}
