package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Empleado;

public interface IEmpleadoUseCase {
	List<Empleado> listarTodos();

	List<Empleado> listarActivos();

	Optional<Empleado> buscarPorId(Long id);

	Empleado guardar(Empleado empleado);

	Empleado actualizar(Long id, Empleado empleado);

	void eliminar(Long id);
}
