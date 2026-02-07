package com.sifuturo.sigep.aplicacion.casosuso.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.ILoginUseCase;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.presentacion.dto.LoginRequestDto;
import com.sifuturo.sigep.presentacion.dto.MenuDto;
import com.sifuturo.sigep.presentacion.dto.response.LoginResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Para usar streams

@Service
public class LoginUseCaseImpl implements ILoginUseCase {

	private final IUsuarioRepositorio usuarioRepositorio;
	private final PasswordEncoder passwordEncoder;

	private static final int MAX_INTENTOS = 3;
    private static final int TIEMPO_BLOQUEO_MINUTOS = 15;
    
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
		// 2. VERIFICAR SI ESTÁ BLOQUEADO POR INTENTOS FALLIDOS
        if (usuario.getFechaBloqueo() != null) {
            if (usuario.getFechaBloqueo().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Cuenta bloqueada temporalmente por intentos fallidos. " +
                        "Intente nuevamente después de " + TIEMPO_BLOQUEO_MINUTOS + " minutos.");
            } else {
                // El tiempo ya pasó, desbloqueamos
                usuario.setFechaBloqueo(null);
                usuario.setIntentosFallidos(0);
                usuarioRepositorio.guardar(usuario);
            }
        }
		
		
     // 3. COMPARAR CONTRASEÑA
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            // -- LÓGICA DE FALLO --
            int intentos = usuario.getIntentosFallidos() == null ? 0 : usuario.getIntentosFallidos();
            intentos++;
            
            usuario.setIntentosFallidos(intentos);

            if (intentos >= MAX_INTENTOS) {
                usuario.setFechaBloqueo(LocalDateTime.now().plusMinutes(TIEMPO_BLOQUEO_MINUTOS));
                usuarioRepositorio.guardar(usuario);
                throw new RuntimeException("Has excedido el número de intentos. Tu cuenta ha sido bloqueada por " + TIEMPO_BLOQUEO_MINUTOS + " minutos.");
            }

            usuarioRepositorio.guardar(usuario);
            throw new RuntimeException("Usuario o contraseña incorrectos. Intentos restantes: " + (MAX_INTENTOS - intentos));
        }

     // 4. VERIFICAR ESTADO (Inactivo administrativo)
        if (!usuario.getEstado()) {
            throw new RuntimeException("El usuario está inactivo en el sistema.");
        }

        // 5. LOGIN EXITOSO -> RESETEAR CONTADORES
        if (usuario.getIntentosFallidos() != null && usuario.getIntentosFallidos() > 0) {
            usuario.setIntentosFallidos(0);
            usuario.setFechaBloqueo(null);
            usuarioRepositorio.guardar(usuario);
        }

     // 6. GENERAR ROLES Y MENÚS (Tu lógica original)
        List<String> rolesUsuario = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toList());

        List<MenuDto> menusPersonalizados = fabricarMenus(rolesUsuario);

        LoginResponseDto response = new LoginResponseDto();
        response.setUsername(usuario.getUsername());
        response.setMensaje("Bienvenido al sistema SIGEP");
        response.setAccesoConcedido(true);
        response.setRoles(rolesUsuario);
        response.setMenus(menusPersonalizados);

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

	    // 1. MENÚS COMUNES (Para todos)
	    menus.add(new MenuDto("Inicio", "/home", "home"));
	    menus.add(new MenuDto("Mi Perfil", "/mi-perfil", "user")); 
	    menus.add(new MenuDto("Mis Timbradas", "/mis-timbradas", "clock"));
	    menus.add(new MenuDto("Mis Permisos", "/mis-permisos", "file-text")); 
	    menus.add(new MenuDto("Mi Ficha Médica", "/mi-ficha-medica", "heart")); 

	    // 2. MENÚ PARA JEFES (NUEVO: ROLE_JEFE o ROLE_DIRECTOR)
	    // Agregamos ROLE_JEFE para que puedan entrar a aprobar
	    if (roles.contains("ROLE_JEFE") || roles.contains("ROLE_TH") || roles.contains("ROLE_ADMIN")) {
	        menus.add(new MenuDto("Bandeja Solicitudes", "/gestion-solicitudes", "check-square"));
	    }

	    // 3. MENÚS ADMINISTRATIVOS (Talento Humano y Admin solamente)
	    if (roles.contains("ROLE_TH") || roles.contains("ROLE_ADMIN")) {
	        menus.add(new MenuDto("Gestión Personal", "/gestion-personal", "users"));
	        menus.add(new MenuDto("Gestión Usuarios", "/gestion-usuarios", "lock"));
	        menus.add(new MenuDto("Gestión Áreas", "/gestion-areas", "building"));
	        menus.add(new MenuDto("Reporte Asistencia", "/reporte-asistencia", "bar-chart"));
	    }

	    // 4. MENÚS MÉDICOS
	    if (roles.contains("ROLE_DOCTOR") || roles.contains("ROLE_ADMIN")) {
	        menus.add(new MenuDto("Gestión Médica", "/gestion-medica", "medicine-box"));
	    }

	    return menus;
	}
}