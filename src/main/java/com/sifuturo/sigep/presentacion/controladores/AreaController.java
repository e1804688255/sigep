package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IAreaUseCase; // Usa la Interfaz, es mejor práctica
import com.sifuturo.sigep.dominio.entidades.Area;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AreaController {

    private final IAreaUseCase areaUseCase; 

    @GetMapping
    public ResponseEntity<List<Area>> listar() {
        return ResponseEntity.ok(areaUseCase.listarActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Area> buscarPorId(@PathVariable Long id) {
        return areaUseCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Area> crear(@RequestBody Area area) {
        Area nuevaArea = areaUseCase.guardar(area);
        return new ResponseEntity<>(nuevaArea, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> actualizar(@PathVariable Long id, @RequestBody Area area) {
        return ResponseEntity.ok(areaUseCase.actualizar(id, area));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        areaUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}