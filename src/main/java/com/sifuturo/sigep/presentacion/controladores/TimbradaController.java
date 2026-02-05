package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.ITimbradaUseCase;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/timbradas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class TimbradaController {

    private final ITimbradaUseCase timbradaUseCase;

    @PostMapping
    public ResponseEntity<Timbrada> registrarTimbrada(@RequestBody RegistrarTimbradaDto dto) {
        Timbrada nuevaTimbrada = timbradaUseCase.registrar(dto);
        return ResponseEntity.ok(nuevaTimbrada);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<Timbrada>> listarTimbradasPorEmpleado(@PathVariable Long idEmpleado) {
        List<Timbrada> historial = timbradaUseCase.listarMisTimbradas(idEmpleado);
        return ResponseEntity.ok(historial);
    }
}