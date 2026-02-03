package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.presentacion.dto.EmpleadoDto;
import com.sifuturo.sigep.presentacion.mapeadores.IEmpleadoDtoMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpleadoController {

	private final IEmpleadoUseCase useCase;
	private final IEmpleadoDtoMapper mapper;

	@GetMapping
	public ResponseEntity<List<Empleado>> listar() {
		return ResponseEntity.ok(useCase.listarActivos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {
		return useCase.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EmpleadoDto> crear(@RequestBody EmpleadoDto empleadoDto) {
		Empleado empleadoDominio = mapper.toDomain(empleadoDto);
		Empleado empleadoGuardado = useCase.guardar(empleadoDominio);

		return new ResponseEntity<>(mapper.toDto(empleadoGuardado), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
		return ResponseEntity.ok(useCase.actualizar(id, empleado));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		useCase.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
