package com.sifuturo.sigep.infraestructura.configuracion;

import com.sifuturo.sigep.dominio.entidades.*;
import com.sifuturo.sigep.dominio.repositorios.*;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IPersonaRepositorio personaRepo;
    private final IEmpleadoRepositorio empleadoRepo;
    private final IRolRepositorio rolRepo;
    private final IUsuarioUseCase usuarioUseCase;

    @Override
    public void run(String... args) throws Exception {
        // 1. CREAR ROLES BÁSICOS (Si no existen)
        if (rolRepo.listarTodos().isEmpty()) {
            rolRepo.guardar(Rol.builder().nombre("ROLE_ADMIN").descripcion("Acceso total").estado(true).build());
            rolRepo.guardar(Rol.builder().nombre("ROLE_JEFE").descripcion("Acceso a gestión").estado(true).build());
            rolRepo.guardar(Rol.builder().nombre("ROLE_EMPLEADO").descripcion("Acceso base").estado(true).build());
        }

        // 2. CREAR LA PERSONA (El "Jefe de Jefes")
        if (usuarioUseCase.listarTodos().isEmpty()) {
            Persona personaAdmin = Persona.builder()
                    .nombres("SUPER")
                    .apellidos("ADMINISTRADOR")
                    .cedula("0000000000") // Pon una cédula real si tienes validación
                    .estado(true)
                    .build();
            Persona personaGuardada = personaRepo.crear(personaAdmin);

            // 3. CREAR EL EMPLEADO
            Empleado empleadoAdmin = Empleado.builder()
                    .persona(personaGuardada)
                    .codigoEmpleado("EMP-001")
                    .estado(true)
                    .build();
            Empleado empleadoGuardado = empleadoRepo.guardar(empleadoAdmin);

            // 4. CREAR EL USUARIO USANDO EL CASO DE USO (Para que use tu lógica de username)
            CrearUsuarioDto dto = new CrearUsuarioDto();
            dto.setIdEmpleado(empleadoGuardado.getIdEmpleado());
            dto.setPassword("admin123"); // El UseCase se encargará de encriptarla
            
            // Asignamos el ID del rol ROLE_ADMIN (asumiendo que es el ID 1)
            dto.setRolesIds(Arrays.asList(1L)); 

            usuarioUseCase.registrarUsuario(dto);
            
            System.out.println("✅ Usuario ADMIN creado exitosamente: SADMINISTRADOR");
        }
    }
}