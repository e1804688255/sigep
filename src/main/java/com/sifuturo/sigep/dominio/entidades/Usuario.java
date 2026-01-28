package com.sifuturo.sigep.dominio.entidades;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadBase {

	private Long idUsuario;
	private String username;
	private String password; // En el futuro esto estará encriptado
	private List<Rol> roles;

	private Empleado empleado;
}