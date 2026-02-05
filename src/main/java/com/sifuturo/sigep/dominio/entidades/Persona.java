package com.sifuturo.sigep.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sifuturo.sigep.dominio.entidades.enums.EstadoPersona;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
public class Persona extends EntidadBase {
    private Long id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String celular;
    private LocalDate fechaNacimiento;
    private EstadoPersona estadoPersona; 
    private BigDecimal aspiracionSalarial; 
    private String hojaVidaBase64;
    private LocalDate fechaPostulacion; 
    private CargoEntity cargoPostulacion;
}