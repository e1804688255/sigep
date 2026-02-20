package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IGestionarAusenciaUseCase;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Importante para que el Front conecte
public class SolicitudAusenciaController {

    private final IGestionarAusenciaUseCase gestionarAusenciaUseCase;

    @GetMapping
    public ResponseEntity<List<SolicitudAusenciaDTO>> listarTodas() {
        return ResponseEntity.ok(gestionarAusenciaUseCase.listarTodas());
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<SolicitudAusenciaDTO>> listarPorEmpleado(@PathVariable Long idEmpleado) {
        return ResponseEntity.ok(gestionarAusenciaUseCase.listarPorEmpleado(idEmpleado));
    }

    @PostMapping
    public ResponseEntity<SolicitudAusenciaDTO> crear(@RequestBody SolicitudAusenciaDTO dto) {
        return ResponseEntity.ok(gestionarAusenciaUseCase.crearSolicitud(dto));
    }

    // Corregido: La sintaxis del Map estaba un poco rota en tu código original
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudAusenciaDTO> aprobar(
            @PathVariable Long id, 
            @RequestBody Map<String, Long> payload) {
        
        Long idEjecutor = payload.get("ejecutadoPor"); 
        return ResponseEntity.ok(gestionarAusenciaUseCase.procesarAprobacion(id, idEjecutor));
    }
    
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudAusenciaDTO> rechazar(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> payload) {
        
        String motivo = (String) payload.get("motivo");
        return ResponseEntity.ok(gestionarAusenciaUseCase.rechazar(id, motivo));
    }
}