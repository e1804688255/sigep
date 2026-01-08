package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "empleados")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;

    // Relación 1 a 1 con PersonaJpaEntity
    // Asegúrate de que PersonaJpaEntity exista en este mismo paquete
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false, unique = true)
    private PersonaEntity persona;

    @Column(name = "codigo_empleado", nullable = false, unique = true)
    private String codigoEmpleado;

    private String cargo;
    private String departamento;
    
    @Column(name = "tipo_contrato")
    private String tipoContrato;
    
    @Column(name = "modalidad_trabajo")
    private String modalidadTrabajo;

    @Column(name = "salario_base")
    private BigDecimal salarioBase;
    
    private String moneda;
    
    @Column(name = "email_corporativo", unique = true)
    private String emailCorporativo;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;
    
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    private String estado;

    @CreatedDate
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @LastModifiedDate
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}