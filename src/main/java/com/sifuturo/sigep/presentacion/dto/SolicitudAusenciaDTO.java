package com.sifuturo.sigep.presentacion.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudAusenciaDTO {
    private Long id;
    private Long idEmpleado;        
    private String nombreEmpleado;  
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
    private String tipo;            
    private String estadoSolicitud; 
    private String evidenciaBase64; 

    // --- AGREGA ESTO para que la tabla muestre cuándo se creó ---
    private LocalDateTime fechaSolicitud; 
    
    private String observaciones; 
}