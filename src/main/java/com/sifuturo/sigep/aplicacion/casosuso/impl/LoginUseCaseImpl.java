package com.sifuturo.sigep.aplicacion.casosuso.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.ILoginUseCase;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.presentacion.dto.LoginRequestDto;
import com.sifuturo.sigep.presentacion.dto.MenuDto;
import com.sifuturo.sigep.presentacion.dto.response.LoginResponseDto;

import java.util.ArrayList;
import java.util.List;
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
		Usuario usuario = usuarioRepositorio.buscarPorUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos")); // Mensaje genérico por
																								// seguridad

		// 2. COMPARAR LA CONTRASEÑA ENCRIPTADA
		// El método matches toma (contraseñaPlana, contraseñaHashDeLaBD)
		if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
			throw new RuntimeException("Usuario o contraseña incorrectos");
		}

		// 3. Si pasa, verificar estado
		if (!usuario.getEstado()) {
			throw new RuntimeException("El usuario está inactivo");
		}

		// 2. Obtener roles como Strings
        List<String> rolesUsuario = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toList());

        // 3. FABRICAR MENÚS SEGÚN TUS REGLAS DE NEGOCIO
        List<MenuDto> menusPersonalizados = fabricarMenus(rolesUsuario);

        // 4. Armar respuesta
        LoginResponseDto response = new LoginResponseDto();
        response.setUsername(usuario.getUsername());
        response.setMensaje("Bienvenido al sistema SIGEP");
        response.setAccesoConcedido(true);
        response.setRoles(rolesUsuario);
        response.setMenus(menusPersonalizados); // Enviamos los menús al frontend

        if (usuario.getEmpleado() != null) {
            response.setIdEmpleado(usuario.getEmpleado().getIdEmpleado());
        }
        return response;
    }

    /**
     * Define qué ve cada rol basado en las reglas de negocio.
     */
    private List<MenuDto> fabricarMenus(List<String> roles) {
        List<MenuDto> menus = new ArrayList<>();

        // --- REGLA 1: TODOS LOS EMPLEADOS (Base) ---
        // "todos puedan tener acceso al modulo de timbradas y a su ficha medica"
        // "actualizacion de su perfil todos direcciones foto y asi"
        menus.add(new MenuDto("Inicio", "/home", "home"));
        menus.add(new MenuDto("Mi Perfil", "/mi-perfil", "user")); // Editar foto, dirección
        menus.add(new MenuDto("Mis Timbradas", "/mis-timbradas", "clock"));
        menus.add(new MenuDto("Mi Ficha Médica", "/mi-ficha-medica", "heart")); // Solo ver

        // --- REGLA 2: TALENTO HUMANO (TH) ---
        // "crear nuevos empleaos solo lso ed th"
        // "moverlos por areas solo th" (Esto se hace en gestión de empleados)
        // "creacion de usuario solo th"
        if (roles.contains("ROLE_TH") || roles.contains("ROLE_ADMIN")) {
            menus.add(new MenuDto("Gestión Empleados", "/gestion-empleados", "users")); // Crear, Editar, Mover Área
            menus.add(new MenuDto("Gestión Usuarios", "/gestion-usuarios", "lock"));   // Crear usuarios
            menus.add(new MenuDto("Gestión Áreas", "/gestion-areas", "building"));
        }

        // --- REGLA 3: DOCTOR ---
        // "modificar ficha medica pues solo el doctor de th"
        if (roles.contains("ROLE_DOCTOR")) {
            menus.add(new MenuDto("Gestión Médica", "/gestion-medica", "kit-medical")); // Buscar empleado y editar su ficha
        }

        return menus;
    }
}