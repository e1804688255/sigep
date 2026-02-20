package com.sifuturo.sigep.presentacion.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RegistrarFichaDto {
    private Long idEmpleado;      
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String doctorNombre;
    private LocalDateTime fechaConsulta;
    
    // --- AGREGAMOS ESTOS CAMPOS QUE FALTABAN ---
    private String presion;
    private String temperatura;
    private String peso;
}