package com.sifuturo.sigep.aplicacion.casosuso.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.ILoginUseCase;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.presentacion.dto.LoginRequestDto;
import com.sifuturo.sigep.presentacion.dto.LoginResponseDto;
import java.util.stream.Collectors; // Para usar streams

@Service
public class LoginUseCaseImpl implements ILoginUseCase {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    


    public LoginUseCaseImpl(IUsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
    public LoginResponseDto ingresar(LoginRequestDto request) {
		// 1. Buscar al usuario por username
        Usuario usuario = usuarioRepositorio.buscarPorUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos")); // Mensaje genérico por seguridad

        // 2. COMPARAR LA CONTRASEÑA ENCRIPTADA
        // El método matches toma (contraseñaPlana, contraseñaHashDeLaBD)
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // 3. Si pasa, verificar estado
        if (!usuario.getEstado()) {
            throw new RuntimeException("El usuario está inactivo");
        }

        // 4. Construir respuesta (Token, Roles, etc.)
        LoginResponseDto response = new LoginResponseDto();
        response.setUsername(usuario.getUsername());
        response.setMensaje("Bienvenido al sistema SIGEP");
        response.setAccesoConcedido(true);
        
        // Convertir roles a lista de Strings
        response.setRoles(usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toList()));

        return response;
    }
}