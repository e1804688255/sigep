package com.sifuturo.sigep.dominio.entidades;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Usuario extends EntidadBase {

	private Long idUsuario;
	private String username;
	private String password; 
	private List<Rol> roles;
	private Empleado empleado;
}