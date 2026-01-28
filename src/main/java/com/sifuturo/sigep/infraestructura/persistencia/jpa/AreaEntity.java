package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "areas")
@Data
@EqualsAndHashCode(callSuper = true)
public class AreaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(length = 255)
    private String descripcion;
}