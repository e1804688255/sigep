package com.sifuturo.sigep.dominio.entidades;

import java.time.LocalDateTime;

import com.sifuturo.sigep.dominio.entidades.enums.TipoTimbrada;

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
public class Timbrada extends EntidadBase {

    private Long id;
    private LocalDateTime fechaHora; 
    private TipoTimbrada tipo;       
    private Double latitud;        
    private Double longitud;
    private String observacion;     
    
    private Empleado empleado;
}