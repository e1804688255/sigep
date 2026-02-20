package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IPersonaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IPersonaJpaRepository;
import lombok.RequiredArgsConstructor;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Repository
@RequiredArgsConstructor
public class PersonaRepositorioAdapter implements IPersonaRepositorio {

	private final IPersonaJpaRepository jpaRepository;
	private final IPersonaMapper mapper;

	@Override
	public Persona crear(Persona persona) {
	    PersonaEntity entity;

	    if (persona.getId() != null) {
	        // 1. Buscamos la entidad original de la DB (Trae el estado, auditoría, etc.)
	        entity = jpaRepository.findById(persona.getId())
	                .orElseThrow(() -> new RuntimeException("No existe la entidad"));
	        
	        // 2. Usamos el método de "Update" del mapper para que SOLO cambie lo que viene en 'persona'
	        // Esto NO toca los campos que no estén en el objeto 'persona'
	        mapper.updateEntityFromDomain(persona, entity);
	    } else {
	        // Es nuevo
	        entity = mapper.toEntity(persona);
	    }

	    // 3. Al guardar 'entity', Hibernate mantiene lo que ya tenía (como el estado)
	    PersonaEntity guardado = jpaRepository.save(entity);
	    return mapper.toDomain(guardado);
	}

	@Override
	public Optional<Persona> obtenerPorCedula(String cedula) {
		return jpaRepository.findByCedula(cedula).map(mapper::toDomain);
	}

	@Override
	public Optional<Persona> buscarPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<Persona> listarTodos() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Persona> listarActivos() {
		return jpaRepository.findByEstadoTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public boolean existsByCedula(String cedula) {
		return jpaRepository.existsByCedula(cedula);
	}

	@Override
	public boolean existePorId(Long id) {
		return jpaRepository.existsById(id);
	}
}