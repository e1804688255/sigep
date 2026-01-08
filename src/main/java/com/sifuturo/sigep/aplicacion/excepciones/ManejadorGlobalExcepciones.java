package com.sifuturo.sigep.aplicacion.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;

@ControllerAdvice
@RestController
public class ManejadorGlobalExcepciones {
	// 1. Manejar error cuando no se encuentra algo (404 Not Found)
	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<RespuestaError> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex,
			WebRequest request) {
		RespuestaError error = new RespuestaError(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// 2. Manejar reglas de negocio rotas (400 Bad Request)
	@ExceptionHandler(ReglaNegocioException.class)
	public ResponseEntity<RespuestaError> manejarReglaNegocio(ReglaNegocioException ex, WebRequest request) {
		RespuestaError error = new RespuestaError(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// 3. Manejar cualquier otro error inesperado (500 Internal Server Error)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError> manejarErrorGlobal(Exception ex, WebRequest request) {
		RespuestaError error = new RespuestaError("Ocurrió un error interno", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}