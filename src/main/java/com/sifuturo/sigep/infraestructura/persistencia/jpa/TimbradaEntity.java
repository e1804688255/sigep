package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import java.time.LocalDateTime;

import com.sifuturo.sigep.dominio.entidades.enums.TipoTimbrada;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "timbradas")
@Data
@EqualsAndHashCode(callSuper = true)
public class TimbradaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTimbrada tipo;

    private Double latitud;
    private Double longitud;
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private EmpleadoEntity empleado;
}