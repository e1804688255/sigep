package com.sifuturo.sigep.dominio.repositorios;

import com.sifuturo.sigep.dominio.entidades.Cargo;

import java.util.List;
import java.util.Optional;

public interface ICargoRepositorio {
	Optional<Cargo> buscarPorId(Long id);

	List<Cargo> listarTodos();

	List<Cargo> listarActivos();

	Cargo guardar(Cargo cargo);

	boolean existePorNombre(String nombre);
}