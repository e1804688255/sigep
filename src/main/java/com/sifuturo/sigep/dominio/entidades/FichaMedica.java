package com.sifuturo.sigep.dominio.entidades;

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

public class FichaMedica extends EntidadBase {

	private Long id;
    private String tipoSangre;
    private String alergias;
    private String enfermedades;
    private String medicacion;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    private Persona persona;
}
