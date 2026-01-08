package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Empleado;

public interface IEmpleadoRepositorio {
	Empleado guardar(Empleado empleado);

	Optional<Empleado> buscarPorId(Long id);

	Optional<Empleado> buscarPorCodigo(String codigo);

	List<Empleado> listarTodos();

	void eliminar(Long id);
}
