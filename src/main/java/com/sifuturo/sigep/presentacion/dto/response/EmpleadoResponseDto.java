package com.sifuturo.sigep.presentacion.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sifuturo.sigep.presentacion.dto.AreaDto;
import com.sifuturo.sigep.presentacion.dto.CargoDto;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;

import lombok.Data;

@Data
public class EmpleadoResponseDto {
    private Long idEmpleado;
    private PersonaDTO persona; // Datos personales completos
    private AreaDto area;       // Objeto completo
    private CargoDto cargo;     // Objeto completo
    private String codigoEmpleado;
    private String tipoContrato;
    private String emailCorporativo;
    private LocalDate fechaContratacion;
    
    // Para mostrar quién es el jefe
    private String nombreJefeInmediato; 
    private Long idJefeInmediato;
}