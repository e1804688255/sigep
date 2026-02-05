package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fichas_medicas")
@Data
public class FichaMedicaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoSangre;
    private String alergias;
    private String enfermedades;
    private String medicacion;
    private String contactoEmergencia;
    private String telefonoEmergencia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona; 
}