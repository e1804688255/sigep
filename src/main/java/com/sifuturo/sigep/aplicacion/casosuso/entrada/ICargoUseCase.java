package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;
import com.sifuturo.sigep.dominio.entidades.Cargo;

public interface ICargoUseCase {
    List<Cargo> listarActivos();
    Cargo guardar(Cargo cargo);
    Optional<Cargo> buscarPorId(Long id);
    void eliminar(Long id);
}