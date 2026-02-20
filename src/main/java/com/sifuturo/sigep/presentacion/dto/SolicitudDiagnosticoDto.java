package com.sifuturo.sigep.presentacion.dto;

import lombok.Data;

@Data
public class SolicitudDiagnosticoDto {
	private String motivo;
	private String presion;
	private String temperatura;
	private String peso;
}