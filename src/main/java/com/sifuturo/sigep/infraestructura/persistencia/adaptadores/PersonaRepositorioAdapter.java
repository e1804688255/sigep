package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IPersonaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IPersonaJpaRepository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Component
public class PersonaRepositorioAdapter implements IPersonaRepositorio {

    private final IPersonaJpaRepository jpaRepository;
    private final IPersonaMapper mapper;
    
    public PersonaRepositorioAdapter(IPersonaJpaRepository jpaRepository, IPersonaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Persona crear(Persona persona) {
        // 1. Convertir Dominio -> Entity
        PersonaEntity entity = mapper.toEntity(persona);
        
        // 2. Guardar en Base de Datos
        PersonaEntity guardado = jpaRepository.save(entity);
        
        // 3. Devolver Dominio
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<Persona> obtenerPorCedula(String cedula) {
        // Buscar y mapear si existe
        return jpaRepository.findByCedula(cedula)
                .map(mapper::toDomain);
    }

    @Override
    public List<Persona> listar() {
        // Obtener lista de entidades y convertirlas una por una a dominio
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain) // Referencia a método
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(int id) {
        // Ojo: Tu entity usa Long, pero la interfaz int. Hacemos casting.
        jpaRepository.deleteById((long) id);
    }
    
    @Override
    public Optional<Persona> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
}