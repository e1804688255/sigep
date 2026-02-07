package com.sifuturo.sigep.presentacion.controladores;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IGestionarAusenciaUseCase; // La interfaz
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudAusenciaController {

	private final IGestionarAusenciaUseCase gestionarAusenciaUseCase;

	@GetMapping
	public ResponseEntity<List<SolicitudAusenciaDTO>> listarTodas() {
		return ResponseEntity.ok(gestionarAusenciaUseCase.listarTodas());
	}

	// GET /api/solicitudes/empleado/1
	@GetMapping("/empleado/{idEmpleado}")
	public ResponseEntity<List<SolicitudAusenciaDTO>> listarPorEmpleado(@PathVariable Long idEmpleado) {
		return ResponseEntity.ok(gestionarAusenciaUseCase.listarPorEmpleado(idEmpleado));
	}

	@PutMapping("/{id}/aprobar")
	public ResponseEntity<SolicitudAusenciaDTO> aprobar(
	        @PathVariable Long id, 
	        @RequestBody Map<String, Long> payload) {
	    
	    // Recibimos quien hace la acción desde el Front
	    Long idEjecutor = payload.get("ejecutadoPor"); 
	    return ResponseEntity.ok(gestionarAusenciaUseCase.procesarAprobacion(id, idEjecutor));
	}
    
    // El de rechazar debe ser diferente
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudAusenciaDTO> rechazar(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> payload) {
        
        String motivo = (String) payload.get("motivo");
        return ResponseEntity.ok(gestionarAusenciaUseCase.rechazar(id, motivo));
    }

	@PostMapping
	public ResponseEntity<SolicitudAusenciaDTO> crear(@RequestBody SolicitudAusenciaDTO dto) {
		return ResponseEntity.ok(gestionarAusenciaUseCase.crearSolicitud(dto));
	}

	
}