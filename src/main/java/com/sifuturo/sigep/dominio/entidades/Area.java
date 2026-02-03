package com.sifuturo.sigep.dominio.entidades;

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
public class Area extends EntidadBase {
    private Long id;
    private String nombre; // Ej: "Tecnología", "Talento Humano"
    private String codigo; // Ej: "TI", "TH"
    private String descripcion;
}