package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.ICargoMapper;
import com.sifuturo.sigep.infraestructura.repositorios.ICargoJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CargoRepositorioAdapter implements ICargoRepositorio {

	private final ICargoJpaRepository jpaRepository;
	private final ICargoMapper mapper;

	@Override
	public Optional<Cargo> buscarPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);

	}

	@Override
	public List<Cargo> listarTodos() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Cargo> listarActivos() {
		return jpaRepository.findByEstadoTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public Cargo guardar(Cargo cargo) {
		CargoEntity entity = mapper.toEntity(cargo);
		CargoEntity guardado = jpaRepository.save(entity);
		return mapper.toDomain(guardado);
	}


	@Override
	public boolean existePorNombre(String nombre) {
		return jpaRepository.existsByNombre(nombre);
		}
}