package com.sifuturo.sigep.dominio.repositorios;

import com.sifuturo.sigep.dominio.entidades.Area;
import java.util.Optional;

public interface IAreaRepositorio {
    Optional<Area> buscarPorId(Long id);
}