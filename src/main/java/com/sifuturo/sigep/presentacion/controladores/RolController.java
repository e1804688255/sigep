package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.IRolUseCase;
import com.sifuturo.sigep.dominio.entidades.Rol;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rol")
@RequiredArgsConstructor
public class RolController {
    private final IRolUseCase rolUseCase; 

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        return ResponseEntity.ok(rolUseCase.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
        return rolUseCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        Rol nuevRol = rolUseCase.guardar(rol);
        return new ResponseEntity<>(nuevRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id, @RequestBody Rol rol) {
        return ResponseEntity.ok(rolUseCase.actualizar(id, rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    	rolUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}