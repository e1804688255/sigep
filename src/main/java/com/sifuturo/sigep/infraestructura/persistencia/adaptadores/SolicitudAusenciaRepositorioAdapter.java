package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.dominio.repositorios.ISolicitudAusenciaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.SolicitudAusenciaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.ISolicitudAusenciaEntityMapper;
import com.sifuturo.sigep.infraestructura.repositorios.ISolicitudAusenciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SolicitudAusenciaRepositorioAdapter implements ISolicitudAusenciaRepositorio {

	private final ISolicitudAusenciaJpaRepository jpaRepository;
	private final ISolicitudAusenciaEntityMapper mapper;

	@Override
	public SolicitudAusencia guardar(SolicitudAusencia solicitud) {
		SolicitudAusenciaEntity entity = mapper.toEntity(solicitud);
		SolicitudAusenciaEntity saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<SolicitudAusencia> obtenerPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<SolicitudAusencia> listarPorEmpleado(Long idEmpleado) {
		// 1. Llamamos al JPA con el nombre correcto "findBy..."
		var entidades = jpaRepository.findByEmpleadoIdEmpleado(idEmpleado);

		// 2. Convertimos la lista de Entidades (BD) a Dominio
		return entidades.stream().map(mapper::toDomain) // Asegúrate que tu mapper tenga toDomain
				.collect(Collectors.toList());
	}

	@Override
	public List<SolicitudAusencia> listarTodas() {
		// CORRECCIÓN: Usamos findAll() que es el método estándar de JPA
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}
}