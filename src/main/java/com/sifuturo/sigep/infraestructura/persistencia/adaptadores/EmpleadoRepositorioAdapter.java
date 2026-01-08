package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.EmpleadoEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IEmpleadoMapper; 
import com.sifuturo.sigep.infraestructura.repositorios.IEmpleadoJpaRepository;

@Component
public class EmpleadoRepositorioAdapter implements IEmpleadoRepositorio {

	private final IEmpleadoJpaRepository jpaRepository;
	private final IEmpleadoMapper mapper; 

	public EmpleadoRepositorioAdapter(IEmpleadoJpaRepository jpaRepository, IEmpleadoMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Empleado guardar(Empleado empleado) {
		EmpleadoEntity entity = mapper.toEntity(empleado);
		EmpleadoEntity guardado = jpaRepository.save(entity);
		return mapper.toDomain(guardado);
	}

	@Override
	public Optional<Empleado> buscarPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Optional<Empleado> buscarPorCodigo(String codigo) {
		return jpaRepository.findByCodigoEmpleado(codigo).map(mapper::toDomain);
	}

	@Override
	public List<Empleado> listarTodos() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public void eliminar(Long id) {
		jpaRepository.deleteById(id);
	}
}