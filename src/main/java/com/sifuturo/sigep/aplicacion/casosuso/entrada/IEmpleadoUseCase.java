package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Empleado;

public interface IEmpleadoUseCase {
	Empleado guardar(Empleado empleado);

	Optional<Empleado> buscarPorId(Long id);

	Optional<Empleado> buscarPorCodigo(String codigo);

	List<Empleado> listarTodos();

	void eliminar(Long id);
}
