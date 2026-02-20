package com.sifuturo.sigep.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Empleado extends EntidadBase {

    private Long idEmpleado;
    private Persona persona;
    private Area area;
    private Cargo cargo;
    private String codigoEmpleado;
    // private String departamento; // <-- RECOMENDACIÓN: Elimina esto si ya tienes el objeto 'Area area'
    
    private String tipoContrato;
    private String modalidadTrabajo;
    private BigDecimal salarioBase;
    private String moneda;
    private String emailCorporativo;
    private LocalDate fechaContratacion;
    private LocalDate fechaFinalizacion;
    private Empleado jefeInmediato;

    // --- NUEVOS CAMPOS ---
    private String banco;
    private String tipoCuentaBancaria;
    private String numeroCuentaBancaria;
}