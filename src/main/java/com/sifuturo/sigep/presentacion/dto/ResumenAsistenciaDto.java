package com.sifuturo.sigep.presentacion.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumenAsistenciaDto {
    private long totalEmpleados;
    private long presentesHoy;
    private long atrasadosHoy;
    private long enVacaciones;
    private List<String> alertasCriticas; 
}