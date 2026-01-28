package com.sifuturo.sigep.presentacion.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearUsuarioDto {
    private Long idEmpleado;
    private String password;
    private List<Long> rolesIds; // IDs de los roles (ej: [1] para ADMIN)
}