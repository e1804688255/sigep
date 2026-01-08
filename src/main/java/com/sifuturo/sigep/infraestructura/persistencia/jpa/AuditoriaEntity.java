package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass // Dice a JPA: "No crees tabla para esto, pero pon estos campos en las tablas
					// hijas"
@EntityListeners(AuditingEntityListener.class) // Activa la escucha automática
public abstract class AuditoriaEntity {
	@CreatedDate
	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime fechaCreacion;

	@CreatedBy
	@Column(name = "usuario_creacion", updatable = false)
	private String usuarioCreacion;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	@LastModifiedBy
	@Column(name = "usuario_modificacion")
	private String usuarioModificacion;

	@Column(name = "estado")
	private Boolean estado = true;
}
