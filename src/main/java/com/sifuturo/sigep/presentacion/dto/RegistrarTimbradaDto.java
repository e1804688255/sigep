package com.sifuturo.sigep.presentacion.dto;

import com.sifuturo.sigep.dominio.entidades.enums.TipoTimbrada;
import lombok.Data;

@Data
public class RegistrarTimbradaDto {
    private Long idEmpleado;       
    private TipoTimbrada tipo;    
    private Double latitud;
    private Double longitud;
    private String observacion;
}