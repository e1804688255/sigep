package com.sifuturo.sigep.dominio.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Rol  extends EntidadBase  { // Rol suele ser estático, a veces no necesita auditoría completa, pero depende de ti.
    private Long id;
    private String nombre; // Ej: "ROLE_ADMIN", "ROLE_USER"
    private String descripcion;
}