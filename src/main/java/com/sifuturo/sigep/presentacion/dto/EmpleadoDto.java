package com.sifuturo.sigep.presentacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.sifuturo.sigep.dominio.entidades.Persona;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDto {

	private Long idEmpleado;
	private Persona persona;
	private String codigoEmpleado;
	private String cargo;
	private String departamento;
	private String tipoContrato;
	private String modalidadTrabajo;
	private BigDecimal salarioBase;
	private String moneda;
	private String emailCorporativo;
	private LocalDate fechaContratacion;
	private LocalDate fechaFinalizacion;
}
