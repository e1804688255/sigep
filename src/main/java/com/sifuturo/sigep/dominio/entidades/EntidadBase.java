package com.sifuturo.sigep.dominio.entidades;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class EntidadBase {
	private LocalDateTime fechaCreacion;
	private String usuarioCreacion;
	private LocalDateTime fechaModificacion;
	private String usuarioModificacion;
	private Boolean estado;

	public EntidadBase() {
		this.estado = true;
		this.fechaCreacion = LocalDateTime.now();
	}
}