package com.sifuturo.sigep.dominio.entidades;

import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudAusencia {
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