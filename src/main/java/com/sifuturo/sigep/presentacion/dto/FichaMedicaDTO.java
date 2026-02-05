package com.sifuturo.sigep.presentacion.dto;

import lombok.Data;

@Data
public class FichaMedicaDTO {
    private Long id;
    private String tipoSangre;
    private String alergias;
    private String enfermedades;
    private String medicacion;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    
    private Long idPersona; 
}