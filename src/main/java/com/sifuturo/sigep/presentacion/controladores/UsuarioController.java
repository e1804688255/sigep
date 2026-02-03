package com.sifuturo.sigep.presentacion.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sifuturo.sigep.aplicacion.casosuso.impl.UsuarioUseCaseImpl;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

	private final UsuarioUseCaseImpl usuarioUseCase;

	@PostMapping
	public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioDto dto) {
		Usuario nuevaArea = usuarioUseCase.registrarUsuario(dto);
		return new ResponseEntity<>(nuevaArea, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.ok(usuarioUseCase.listarActivos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
		return usuarioUseCase.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioUseCase.actualizar(id, usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		usuarioUseCase.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}