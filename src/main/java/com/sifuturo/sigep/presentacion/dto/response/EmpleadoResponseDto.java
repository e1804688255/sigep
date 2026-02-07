package com.sifuturo.sigep.presentacion.dto.response;



import lombok.Data;

@Data
public class EmpleadoResponseDto {
    private Long idEmpleado;
    private PersonaResponseDto persona;
}