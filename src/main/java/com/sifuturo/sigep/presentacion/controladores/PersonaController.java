package com.sifuturo.sigep.presentacion.controladores;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoPersona;
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

	// 1. Crear (Corregido para recibir y devolver DTO)
	@PostMapping
	public ResponseEntity<PersonaDTO> crear(@RequestBody PersonaDTO personaDto) {
		Persona personaDominio = mapper.toDomain(personaDto);
		Persona personaGuardada = useCase.crear(personaDominio);
		return new ResponseEntity<>(mapper.toDto(personaGuardada), HttpStatus.CREATED);
	}

	// 2. Listar Activos (Corregido para devolver lista de DTOs)
	@GetMapping
	public ResponseEntity<List<PersonaDTO>> listar() {
		List<Persona> personas = useCase.listarActivos();
		return ResponseEntity.ok(mapper.toDtoList(personas));
	}

	// 3. Buscar por Cédula (Corregido para devolver DTO)
	@GetMapping("/{cedula}")
	public ResponseEntity<PersonaDTO> obtenerPorCedula(@PathVariable String cedula) {
		return useCase.obtenerPorCedula(cedula).map(persona -> ResponseEntity.ok(mapper.toDto(persona)))
				.orElse(ResponseEntity.notFound().build());
	}

	// 4. Buscar por ID (Corregido para devolver DTO)
	@GetMapping("/id/{id}")
	public ResponseEntity<PersonaDTO> buscarPorId(@PathVariable Long id) {
		return useCase.buscarPorId(id).map(persona -> ResponseEntity.ok(mapper.toDto(persona)))
				.orElse(ResponseEntity.notFound().build());
	}

	// 5. Actualizar (Corregido para recibir DTO y devolver DTO)
	@PutMapping("/{id}")
	public ResponseEntity<PersonaDTO> actualizar(@PathVariable Long id, @RequestBody PersonaDTO personaDto) {
		Persona personaDominio = mapper.toDomain(personaDto);
		Persona personaActualizada = useCase.actualizar(id, personaDominio);
		return ResponseEntity.ok(mapper.toDto(personaActualizada));
	}

	// 6. Eliminar (Se mantiene igual, no devuelve contenido)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		useCase.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/rechazar")
	public ResponseEntity<PersonaDTO> rechazarCandidato(@PathVariable Long id) {
		// Creamos un objeto con el cambio mínimo necesario
		Persona cambios = new Persona();
		cambios.setEstadoPersona(EstadoPersona.RECHAZADO);

		// Tu método 'actualizar' ya se encarga de buscar en la DB,
		// mantener lo que no viene y copiar solo el estado nuevo.
		Persona actualizada = useCase.actualizar(id, cambios);

		return ResponseEntity.ok(mapper.toDto(actualizada));
	}
	
	@PatchMapping("/{id}/foto")
	public ResponseEntity<PersonaDTO> actualizarFotoPerfil(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
		// 1. Obtenemos el Base64 del JSON enviado por el Front
		String base64Image = body.get("foto");
		
		// 2. Creamos una Persona vacía y le seteamos SOLO la foto
		Persona cambios = new Persona();
		cambios.setFotoPerfilBase64(base64Image);

		// 3. Tu UseCase ya se encarga de mezclar esto con los datos existentes
		Persona actualizada = useCase.actualizar(id, cambios);

		// 4. Retornamos el DTO actualizado
		return ResponseEntity.ok(mapper.toDto(actualizada));
	}
}