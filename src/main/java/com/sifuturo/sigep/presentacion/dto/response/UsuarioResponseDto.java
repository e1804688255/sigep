package com.sifuturo.sigep.presentacion.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class UsuarioResponseDto {
    private Long idUsuario;
    private String username;
    private Boolean estado; // Cámbialo de 'boolean' a 'Boolean' para coincidir con el dominio
    private List<RolDto> roles;
    private EmpleadoResponseDto empleado;
	private String ipPermitida;

}