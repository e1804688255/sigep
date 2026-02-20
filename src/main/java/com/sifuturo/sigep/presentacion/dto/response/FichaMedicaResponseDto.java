package com.sifuturo.sigep.presentacion.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FichaMedicaResponseDto {
    private Long id;
    private LocalDateTime fechaConsulta;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String doctorNombre;
    
    // --- AGREGAR ESTOS CAMPOS PARA PODER VERLOS EN LA TABLA ---
    private String presion;
    private String temperatura;
    private String peso;
    private String saturacion; // Si lo agregaste a la entidad
    
    // Datos del empleado
    private Long idEmpleado;
    private String nombreEmpleado; 
    private String cedulaEmpleado;
}