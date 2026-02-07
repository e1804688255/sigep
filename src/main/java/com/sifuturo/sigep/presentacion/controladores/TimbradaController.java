package com.sifuturo.sigep.presentacion.controladores;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<Timbrada>> listarTimbradasPorEmpleado(
        @PathVariable Long idEmpleado,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
    ) {
        // Si inicio y fin son nulos, el UseCase debería traer el historial general
        List<Timbrada> historial = timbradaUseCase.listarMisTimbradasConFiltro(idEmpleado, inicio, fin);
        return ResponseEntity.ok(historial);
    }
    
    
}