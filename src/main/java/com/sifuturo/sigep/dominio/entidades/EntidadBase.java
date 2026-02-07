package com.sifuturo.sigep.dominio.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder // Cambia @Builder (si lo tenías) por @SuperBuilder
@NoArgsConstructor
public abstract class EntidadBase {
	private LocalDateTime fechaCreacion;
	private String usuarioCreacion;
	private LocalDateTime fechaModificacion;
	private String usuarioModificacion;
	private Boolean estado;

}