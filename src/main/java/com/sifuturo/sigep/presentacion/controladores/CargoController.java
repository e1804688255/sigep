package com.sifuturo.sigep.presentacion.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.ICargoUseCase;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.presentacion.dto.CargoDto;
import com.sifuturo.sigep.presentacion.mapeadores.ICargoDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CargoController {

    private final ICargoUseCase cargoUseCase;
    private final ICargoDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<CargoDto>> listar() {
        List<CargoDto> lista = cargoUseCase.listarActivos()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<CargoDto> crear(@RequestBody CargoDto cargoDto) {
        Cargo cargoDominio = mapper.toDomain(cargoDto);
        Cargo cargoGuardado = cargoUseCase.guardar(cargoDominio);
        return new ResponseEntity<>(mapper.toDto(cargoGuardado), HttpStatus.CREATED);
    }
}