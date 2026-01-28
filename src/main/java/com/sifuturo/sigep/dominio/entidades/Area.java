package com.sifuturo.sigep.dominio.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area extends EntidadBase {
    private Long id;
    private String nombre; // Ej: "Tecnología", "Talento Humano"
    private String codigo; // Ej: "TI", "TH"
    private String descripcion;
}