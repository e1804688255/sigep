package com.sifuturo.sigep.presentacion.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sifuturo.sigep.aplicacion.casosuso.impl.UsuarioUseCaseImpl;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;
import com.sifuturo.sigep.presentacion.dto.response.UsuarioResponseDto;
import com.sifuturo.sigep.presentacion.mapeadores.IUsuarioPresentacionMapper;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

	private final UsuarioUseCaseImpl usuarioUseCase;
	private final IUsuarioPresentacionMapper presentacionMapper; 
	@PostMapping
	public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioDto dto) {
		Usuario nuevaArea = usuarioUseCase.registrarUsuario(dto);
		return new ResponseEntity<>(nuevaArea, HttpStatus.CREATED);
	}

	@GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<Usuario> usuarios = usuarioUseCase.listarTodos();
        // Transformamos la lista antes de enviarla al Front
        return ResponseEntity.ok(presentacionMapper.toResponseDtoList(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        return usuarioUseCase.buscarPorId(id)
                .map(presentacionMapper::toResponseDto) // Transformamos el objeto único
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

	@PutMapping("/{id}/reset-password")
	public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
		// El JSON del front envía "nuevaClave"
		String nuevaClave = body.get("nuevaClave");

		if (nuevaClave == null || nuevaClave.isEmpty()) {
			return ResponseEntity.badRequest().body("La nueva clave es requerida");
		}

		usuarioUseCase.resetearPassword(id, nuevaClave);
		return ResponseEntity.ok().body("{\"mensaje\":\"Contraseña actualizada correctamente\"}");
	}

	@PutMapping("/{id}/estado")
	public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody java.util.Map<String, Object> body) {
	    Boolean nuevoEstado = Boolean.valueOf(body.get("estado").toString());
	    usuarioUseCase.cambiarEstado(id, nuevoEstado);
	    return ResponseEntity.ok().body("{\"mensaje\":\"Estado actualizado correctamente\"}");
	}
	
	
}