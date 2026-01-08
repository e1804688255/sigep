package com.sifuturo.sigep.aplicacion.excepciones;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RespuestaError {
	private LocalDateTime fecha;
	private String mensaje;
	private String detalles;

	public RespuestaError(String mensaje, String detalles) {
		this.fecha = LocalDateTime.now();
		this.mensaje = mensaje;
		this.detalles = detalles;
	}
}