package com.sifuturo.sigep.presentacion.controladores;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;
import com.sifuturo.sigep.presentacion.mapeadores.IPersonaDtoMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*") // Permite peticiones desde Angular/React local
public class PersonaController {

	private final IPersonaUseCase useCase;
	private final IPersonaDtoMapper mapper;

	public PersonaController(IPersonaUseCase useCase, IPersonaDtoMapper mapper) {
		this.useCase = useCase;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<PersonaDTO> crear(@RequestBody PersonaDTO personaDto) {
	    System.out.println(">>> LLEGO AL CONTROLLER: " + personaDto.toString());

	    Persona personaDominio = mapper.toDomain(personaDto);
	    
	    System.out.println(">>> SE CONVIRTIO A DOMINIO: " + personaDominio.getCedula() + " - " + personaDominio.getNombres());

	    Persona personaGuardada = useCase.crear(personaDominio);
	    return new ResponseEntity<>(mapper.toDto(personaGuardada), HttpStatus.CREATED);
	}

	@GetMapping("/{cedula}")
	public ResponseEntity<PersonaDTO> obtenerPorCedula(@PathVariable String cedula) {
		return useCase.obtenerPorCedula(cedula).map(persona -> ResponseEntity.ok(mapper.toDto(persona)))
				.orElse(ResponseEntity.notFound().build()); // Si no, devolver 404
	}

	@GetMapping
	public ResponseEntity<List<PersonaDTO>> listar() {
		List<Persona> listaDominio = useCase.listar();
		return ResponseEntity.ok(mapper.toDtoList(listaDominio));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable int id) {
		useCase.eliminar(id);
		return ResponseEntity.noContent().build(); // Devuelve 204 No Content
	}
}