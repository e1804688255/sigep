package com.sifuturo.sigep.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empleado extends EntidadBase {

	private Long idEmpleado;
	private Persona persona;
	private Area area;
	private Cargo cargo;
	private String codigoEmpleado;
	private String departamento;
	private String tipoContrato;
	private String modalidadTrabajo;
	private BigDecimal salarioBase;
	private String moneda;
	private String emailCorporativo;
	private LocalDate fechaContratacion;
	private LocalDate fechaFinalizacion;

}