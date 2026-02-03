package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.repositorios.IAreaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.AreaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IAreaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IAreaJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AreaRepositorioAdapter implements IAreaRepositorio {

	private final IAreaJpaRepository jpaRepository;
	private final IAreaMapper mapper;

	@Override
	public Optional<Area> buscarPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<Area> listarTodos() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Area> listarActivos() {
		return jpaRepository.findByEstadoTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public Area guardar(Area area) {
		AreaEntity entity = mapper.toEntity(area);
		AreaEntity guardado = jpaRepository.save(entity);
		return mapper.toDomain(guardado);
	}

	@Override
	public boolean existePorNombre(String nombre) {
		return jpaRepository.existsByNombre(nombre);
	}
}