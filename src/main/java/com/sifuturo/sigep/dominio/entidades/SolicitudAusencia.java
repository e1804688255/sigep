package com.sifuturo.sigep.dominio.entidades;

import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SolicitudAusencia  extends EntidadBase{
    private Long id;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private String evidenciaBase64;
    private TipoAusencia tipo;
    private EstadoSolicitud estadoSolicitud; // Mismo nombre que en la entidad JPA
    
    // Relación con Empleado (Dominio)
    private Empleado empleado; 
    private String observaciones; // <-- AGREGADO
}