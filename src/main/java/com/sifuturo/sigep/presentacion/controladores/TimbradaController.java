package com.sifuturo.sigep.presentacion.controladores;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todo para evitar errores

import com.sifuturo.sigep.aplicacion.casosuso.entrada.ITimbradaUseCase;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;
import com.sifuturo.sigep.presentacion.dto.response.ReporteAsistenciaConsolidadoDto;

import lombok.RequiredArgsConstructor;

@RestController
// 1. La ruta base incluye "/api"
@RequestMapping("/api/timbradas") 
@RequiredArgsConstructor
// 2. ELIMINAMOS @CrossOrigin de aquí porque ya está en SecurityConfig
public class TimbradaController {

    private final ITimbradaUseCase timbradaUseCase;
    
    // 3. La ruta final queda: /api/timbradas/reporte-general
    @GetMapping("/reporte-general")
    public ResponseEntity<List<Timbrada>> obtenerReporteGeneral(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
        @RequestParam(required = false) Long areaId
    ) {
        // Asegúrate de que tu UseCase tenga este método con estos parámetros
        List<Timbrada> reporte = timbradaUseCase.listarReporteGeneral(inicio, fin, areaId);
        return ResponseEntity.ok(reporte);
    }
    
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
        // Asumiendo que este método existe en tu UseCase
        List<Timbrada> historial = timbradaUseCase.listarMisTimbradasConFiltro(idEmpleado, inicio, fin);
        return ResponseEntity.ok(historial);
    }
    
    @GetMapping("/reporte-calculado")
    public ResponseEntity<List<ReporteAsistenciaConsolidadoDto>> obtenerReporteCalculado(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
        @RequestParam(required = false) Long areaId
    ) {
        List<ReporteAsistenciaConsolidadoDto> reporte = timbradaUseCase.generarReporteCalculado(inicio, fin, areaId);
        return ResponseEntity.ok(reporte);
    }
}