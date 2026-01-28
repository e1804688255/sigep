package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.ICargoMapper;
import com.sifuturo.sigep.infraestructura.repositorios.ICargoJpaRepository; 

import java.util.Optional;

@Repository // <--- ESTO ES LO QUE BUSCA SPRING
@RequiredArgsConstructor
public class CargoRepositorioAdapter implements ICargoRepositorio {

    private final ICargoJpaRepository jpaRepository;
    private final ICargoMapper mapper;

    @Override
    public Optional<Cargo> buscarPorId(Long id) {
        Optional<CargoEntity> entity = jpaRepository.findById(id);
        return entity.map(mapper::toDomain);
    }
}