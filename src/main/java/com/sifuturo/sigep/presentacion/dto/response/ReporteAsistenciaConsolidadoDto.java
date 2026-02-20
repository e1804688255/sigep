package com.sifuturo.sigep.presentacion.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReporteAsistenciaConsolidadoDto {
	private Long idEmpleado;
	private String nombreEmpleado;
	private String area;
	private LocalDate fecha;
	private LocalTime horaEntrada;
	private LocalTime horaSalida;
	private long minutosAtraso;
	private String tiempoExtra;
	private boolean esFalta;
	private String estadoAsistencia;
	private long minutosExcesoAlmuerzo;
	private long minutosAtrasoEntrada;
	private String observacionGps;
}