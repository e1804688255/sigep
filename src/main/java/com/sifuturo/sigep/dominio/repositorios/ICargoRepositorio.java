package com.sifuturo.sigep.dominio.repositorios;

import com.sifuturo.sigep.dominio.entidades.Cargo;
import java.util.Optional;

public interface ICargoRepositorio {
    Optional<Cargo> buscarPorId(Long id);
}