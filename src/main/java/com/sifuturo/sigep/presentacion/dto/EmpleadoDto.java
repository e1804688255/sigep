package com.sifuturo.sigep.presentacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmpleadoDto {

	private Long idEmpleado;
	private PersonaDTO persona;
	private CargoDto cargo;
	private AreaDto area;
	private String codigoEmpleado;
	private String tipoContrato;
	private String modalidadTrabajo;
	private BigDecimal salarioBase;
	private String moneda;
	private String emailCorporativo;
	private LocalDate fechaContratacion;
	private LocalDate fechaFinalizacion;
}