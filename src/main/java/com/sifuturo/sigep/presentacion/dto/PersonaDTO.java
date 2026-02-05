package com.sifuturo.sigep.presentacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private Long id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String celular;
    private LocalDate fechaNacimiento;
    private String estadoPersona; // Se enviará como String "CANDIDATO"
    private BigDecimal aspiracionSalarial;
    private String hojaVidaBase64; // Aquí viaja el string gigante
    private Long idCargoPostulacion; // El ID del cargo (ej: 1 para "Desarrollador")
    private String nombreCargoPostulacion; // Opcional: Para mostrarlo lindo en la tabla del front
}