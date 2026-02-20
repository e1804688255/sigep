package com.sifuturo.sigep.presentacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class EmpleadoDto {

    private Long idEmpleado;
    
    // Para facilitar el guardado desde el front, usamos los IDs directos
    // OJO: Tu mapper debe saber mapear de idPersona -> Entidad Persona
    private Long idPersona; 
    private Long idArea;
    private Long idCargo;
    
    // Si necesitas devolver el objeto completo al listar, puedes mantener estos,
    // pero para guardar es más fácil usar los IDs de arriba.
    private PersonaDTO persona; 
    private AreaDto area;
    private CargoDto cargo;

    private String codigoEmpleado;
    private String tipoContrato;
    private String modalidadTrabajo;
    
    private BigDecimal salarioBase; // En React debes enviarlo con este nombre exacto
    private String moneda;
    
    private String emailCorporativo;
    private LocalDate fechaContratacion;
    private LocalDate fechaFinalizacion;
    
    private Long idJefeInmediato; // En React debes enviar solo el número (ID)

    // --- AGREGAMOS LOS CAMPOS QUE FALTABAN ---
    private String banco;
    private String tipoCuentaBancaria;
    private String numeroCuentaBancaria;
}