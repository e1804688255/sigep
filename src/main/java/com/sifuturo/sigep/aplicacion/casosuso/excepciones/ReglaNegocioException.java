package com.sifuturo.sigep.aplicacion.casosuso.excepciones;

public class ReglaNegocioException extends RuntimeException {
	public ReglaNegocioException(String mensaje) {
		super(mensaje);
	}
}