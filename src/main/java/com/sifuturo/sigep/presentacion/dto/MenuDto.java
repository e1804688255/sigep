package com.sifuturo.sigep.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private String titulo; // Ej: "Mis Timbradas"
    private String ruta;   // Ej: "/timbradas"
    private String icono;  // Ej: "clock-icon"
}