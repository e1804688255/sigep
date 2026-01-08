package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;

public class EmpleadoUseCaseImpl implements IEmpleadoUseCase {

	private final IEmpleadoRepositorio repositorio;

	public EmpleadoUseCaseImpl(IEmpleadoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Empleado guardar(Empleado empleado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Empleado> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Empleado> buscarPorCodigo(String codigo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Empleado> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub

	}

}
