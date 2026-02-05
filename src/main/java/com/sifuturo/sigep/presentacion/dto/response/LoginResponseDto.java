package com.sifuturo.sigep.presentacion.dto;

import java.util.List; // Importar List
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // Es bueno tener constructor vacío también
public class LoginResponseDto {
	private String username;

	private List<String> roles;

	private String mensaje;
	private boolean accesoConcedido;
}