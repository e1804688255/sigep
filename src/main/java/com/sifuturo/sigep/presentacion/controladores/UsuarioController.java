package com.sifuturo.sigep.presentacion.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sifuturo.sigep.aplicacion.casosuso.impl.UsuarioUseCaseImpl;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCaseImpl usuarioUseCase;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioDto dto) {
        Usuario usuarioCreado = usuarioUseCase.registrarUsuario(dto);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario creado con éxito");
        respuesta.put("username", usuarioCreado.getUsername());
        respuesta.put("id", usuarioCreado.getIdUsuario());

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}