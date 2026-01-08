package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IPersonaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IEmpleadoJpaRepository;


@Component
public class EmpleadoRepositorioAdapter implements IEmpleadoRepositorio {

	private final IEmpleadoJpaRepository jpaRepository;
    private final IPersonaMapper mapper;
	
    
    
	public EmpleadoRepositorioAdapter(IEmpleadoJpaRepository jpaRepository, IPersonaMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
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
