package com.sifuturo.sigep.presentacion.dto.response;

import java.util.List; // Importar List

import com.sifuturo.sigep.presentacion.dto.MenuDto;

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
    private String mensaje;
    private Boolean accesoConcedido;
    private List<String> roles;
    private List<MenuDto> menus;
    private Long idEmpleado;
}