package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fichas_medicas")
@Data
@EqualsAndHashCode(callSuper = true)
public class FichaMedicaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaConsulta;
    private String motivo;
    
    @Column(columnDefinition = "TEXT")
    private String diagnostico;
    
    @Column(columnDefinition = "TEXT")
    private String tratamiento;
    
    private String doctorNombre;

    // --- NUEVOS CAMPOS ---
    private String presion;
    private String temperatura;
    private String peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false) // Agregado nullable=false por seguridad
    private EmpleadoEntity empleado;
}