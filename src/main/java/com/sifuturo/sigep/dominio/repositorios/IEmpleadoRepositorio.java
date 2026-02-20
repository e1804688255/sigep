package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.EmpleadoEntity;

public interface IEmpleadoRepositorio {
	List<Empleado> listarTodos();

	List<Empleado> listarActivos();

	Optional<Empleado> buscarPorId(Long id);

	Empleado guardar(Empleado empleado);

	Optional<Empleado> buscarPorCodigo(String codigo);

	Long obtenerUltimoId();
	
	Optional<Empleado> buscarPorCedula(String cedula);
}
