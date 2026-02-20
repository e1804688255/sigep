package com.sifuturo.sigep.dominio.entidades;

import java.time.LocalDateTime;
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
public class FichaMedica extends EntidadBase {
    
    // NOTA: Si EntidadBase ya tiene 'id', borra esta línea. Si no, déjala.
    private Long id; 
    
    private LocalDateTime fechaConsulta;
    private String motivo;          
    private String diagnostico;
    private String tratamiento;     
    private String doctorNombre;    
    
    private Empleado empleado;

    // --- NUEVOS CAMPOS ---
    private String presion;
    private String temperatura;
    private String peso;
}