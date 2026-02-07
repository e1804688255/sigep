package com.sifuturo.sigep.aplicacion.casosuso.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.ICargoUseCase;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CargoUseCaseImpl implements ICargoUseCase {

    private final ICargoRepositorio cargoRepositorio;

    @Override
    public List<Cargo> listarActivos() {
        return cargoRepositorio.listarActivos();
    }

    @Override
    public Cargo guardar(Cargo cargo) {
        // Aquí podrías validar si ya existe el nombre usando cargoRepositorio.existePorNombre(cargo.getNombre())
        return cargoRepositorio.guardar(cargo);
    }

    @Override
    public Optional<Cargo> buscarPorId(Long id) {
        return cargoRepositorio.buscarPorId(id);
    }
    
    @Override
    public void eliminar(Long id) {
        // Lógica de eliminación (física o lógica según tu repositorio)
        // Por ahora asumiremos que el repo maneja la lógica base
         buscarPorId(id).ifPresent(c -> {
             c.setEstado(false); // Eliminación lógica
             cargoRepositorio.guardar(c);
         });
    }
}