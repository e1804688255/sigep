package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Rol;
import com.sifuturo.sigep.dominio.repositorios.IRolRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.RolEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IRolMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IRolJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RolRepositorioAdapter implements IRolRepositorio {

    private final IRolJpaRepository jpaRepository;
    private final IRolMapper mapper;

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Rol> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rol> listarActivos() {
        return jpaRepository.findByEstadoTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Rol guardar(Rol rol) {
        RolEntity entity = mapper.toEntity(rol);
        RolEntity guardado = jpaRepository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }
}