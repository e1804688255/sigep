package com.sifuturo.sigep.aplicacion.casosuso.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.sifuturo.sigep.dominio.entidades.*;
import com.sifuturo.sigep.dominio.repositorios.*;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto; // Ojo: idealmente usa un objeto de entrada del dominio, pero por rapidez usaremos DTO aquí o mapealo antes.

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioUseCaseImpl implements IUsuarioUseCase { // Implementa tu interfaz IUsuarioUseCase si la tienes

	private final IUsuarioRepositorio usuarioRepositorio;
	private final IEmpleadoRepositorio empleadoRepositorio;
	private final IRolRepositorio rolRepositorio; // Asumiendo que tienes repositorio de roles
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Usuario registrarUsuario(CrearUsuarioDto dto) {
		// 1. Buscar al Empleado
		Empleado empleado = empleadoRepositorio.buscarPorId(dto.getIdEmpleado())
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

		// 2. Generar Username Automático
		String usernameGenerado = generarUsernameUnico(empleado.getPersona().getNombres(),
				empleado.getPersona().getApellidos());

		// 3. Buscar Roles
		Set<Rol> roles = new HashSet<>();
		for (Long rolId : dto.getRolesIds()) {
			rolRepositorio.buscarPorId(rolId).ifPresent(roles::add);
		}

		// 4. Crear el Usuario
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setUsername(usernameGenerado);
		nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptamos
		nuevoUsuario.setEmpleado(empleado);
		nuevoUsuario.setRoles(new java.util.ArrayList<>(roles));
		nuevoUsuario.setEstado(true); // Activo
		nuevoUsuario.setFechaCreacion(LocalDateTime.now());

		return usuarioRepositorio.guardar(nuevoUsuario);
	}

	// --- ALGORITMO DE GENERACIÓN DE USERNAME (EN MAYÚSCULAS) ---
    private String generarUsernameUnico(String nombres, String apellidos) {
        
        // 1. Limpieza: Obtenemos el primer nombre y apellido en MAYÚSCULAS
        String primerNombre = nombres.trim().split(" ")[0].toUpperCase();
        String primerApellido = apellidos.trim().split(" ")[0].toUpperCase();

        // 2. Base: A + LANDAZURI = ALANDAZURI
        String baseUsername = primerNombre.substring(0, 1) + primerApellido;
        String usernameFinal = baseUsername;

        int contador = 1;
        
        // 3. Validación de duplicados (ALANDAZURI1, ALANDAZURI2...)
        while (usuarioRepositorio.existePorUsername(usernameFinal)) {
            usernameFinal = baseUsername + contador;
            contador++;
        }

        return usernameFinal;
    }
}