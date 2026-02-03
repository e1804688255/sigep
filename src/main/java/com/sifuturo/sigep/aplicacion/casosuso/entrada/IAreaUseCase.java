package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Area;

public interface IAreaUseCase {
	List<Area> listarTodos();

	List<Area> listarActivos();

	Optional<Area> buscarPorId(Long id);

	Area guardar(Area area);

	boolean existePorNombre(String nombre);

	Area actualizar(Long id, Area areaEntrada);
	
	void eliminar(Long id);
}
