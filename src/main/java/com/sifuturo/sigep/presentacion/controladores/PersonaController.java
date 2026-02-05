package com.sifuturo.sigep.presentacion.controladores;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;
import com.sifuturo.sigep.presentacion.mapeadores.IPersonaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PersonaController {

	private final IPersonaUseCase useCase;
	private final IPersonaDtoMapper mapper;

	@PostMapping
	public ResponseEntity<PersonaDTO> crear(@RequestBody PersonaDTO personaDto) {
		Persona personaDominio = mapper.toDomain(personaDto);
		Persona personaGuardada = useCase.crear(personaDominio);
		return new ResponseEntity<>(mapper.toDto(personaGuardada), HttpStatus.CREATED);
	}

	@GetMapping("/{cedula}")
	public ResponseEntity<PersonaDTO> obtenerPorCedula(@PathVariable String cedula) {
		return useCase.obtenerPorCedula(cedula).map(persona -> ResponseEntity.ok(mapper.toDto(persona)))
				.orElse(ResponseEntity.notFound().build()); // Si no, devolver 404
	}

	@GetMapping
	public ResponseEntity<List<Persona>> listar() {
		return ResponseEntity.ok(useCase.listarActivos());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Persona> actualizar(@PathVariable Long id, @RequestBody Persona persona) {
		return ResponseEntity.ok(useCase.actualizar(id, persona));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		useCase.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("id/{id}")
	
	public ResponseEntity<Persona> buscarPorId(@PathVariable Long id) {
		return useCase.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}