package com.sifuturo.sigep.dominio.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public abstract class EntidadBase {
	private LocalDateTime fechaCreacion;
	private String usuarioCreacion;
	private LocalDateTime fechaModificacion;
	private String usuarioModificacion;
	private Boolean estado;

}