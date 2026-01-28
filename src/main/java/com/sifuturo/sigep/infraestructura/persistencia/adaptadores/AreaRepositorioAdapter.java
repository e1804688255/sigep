package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.repositorios.IAreaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.AreaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IAreaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IAreaJpaRepository; // Tu JpaRepository

import java.util.Optional;

@Repository 
@RequiredArgsConstructor
public class AreaRepositorioAdapter implements IAreaRepositorio {

    private final IAreaJpaRepository jpaRepository;
    private final IAreaMapper mapper;

    @Override
    public Optional<Area> buscarPorId(Long id) {
        Optional<AreaEntity> entity = jpaRepository.findById(id);
        return entity.map(mapper::toDomain);
    }
}