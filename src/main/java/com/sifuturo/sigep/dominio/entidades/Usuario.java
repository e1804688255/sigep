package com.sifuturo.sigep.dominio.entidades;

import java.time.LocalDateTime; // Importar esto
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Usuario extends EntidadBase { // <-- EntidadBase DEBE tener el campo 'estado'
    private Long idUsuario;
    private String username;
    private String password; 
    private List<Rol> roles;
    private Empleado empleado;
    private Integer intentosFallidos;
    private LocalDateTime fechaBloqueo;
}