package com.sifuturo.sigep.dominio.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cargo extends EntidadBase {
    private Long id;
    private String nombre; // Ej: "Director", "Analista Senior"
    private String descripcion;
}