package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.IFichaMedicaUseCase;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IFichaMedicaMapper; // Importante inyectar el mapper aquí o hacerlo en capa de servicio
import com.sifuturo.sigep.presentacion.dto.RegistrarFichaDto;
import com.sifuturo.sigep.presentacion.dto.response.FichaMedicaResponseDto;

import lombok.RequiredArgsConstructor;
import com.sifuturo.sigep.aplicacion.casosuso.impl.DiagnosticoIAService; // Importar el servicio
import com.sifuturo.sigep.presentacion.dto.SolicitudDiagnosticoDto;
import java.util.Map;

@RestController
@RequestMapping("/api/fichas-medicas")
@RequiredArgsConstructor
public class FichaMedicaController {

	private final IFichaMedicaUseCase fichaUseCase;
	private final IFichaMedicaMapper fichaMapper;
	private final DiagnosticoIAService iaService;

	@PostMapping("/sugerir-diagnostico")
	public ResponseEntity<Map<String, String>> sugerirDiagnostico(@RequestBody SolicitudDiagnosticoDto dto) {
		return ResponseEntity.ok(iaService.generarSugerencia(dto));
	}

	// Obtener historial de un empleado
	@GetMapping("/empleado/{idEmpleado}")
	public ResponseEntity<List<FichaMedicaResponseDto>> obtenerHistorial(@PathVariable Long idEmpleado) {
		List<FichaMedica> historial = fichaUseCase.obtenerHistorialMedico(idEmpleado);

		// Convertimos la lista de Dominios a Lista de DTOs
		List<FichaMedicaResponseDto> response = historial.stream().map(fichaMapper::toResponseDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	// Registrar nueva consulta
	@PostMapping
	public ResponseEntity<FichaMedicaResponseDto> registrarConsulta(@RequestBody RegistrarFichaDto dto) {
		FichaMedica creada = fichaUseCase.registrarConsulta(dto);
		return ResponseEntity.ok(fichaMapper.toResponseDto(creada));
	}
}