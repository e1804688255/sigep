package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Rol;
import com.sifuturo.sigep.dominio.repositorios.IRolRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.RolEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IRolMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IRolJpaRepository;

import java.util.Optional;

@Repository // <--- ESTO ES LO QUE SPRING ESTÁ BUSCANDO
@RequiredArgsConstructor
public class RolRepositorioAdapter implements IRolRepositorio {

    private final IRolJpaRepository jpaRepository;
    private final IRolMapper mapper;

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        Optional<RolEntity> entity = jpaRepository.findById(id);
        return entity.map(mapper::toDomain);
    }
}