package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sifuturo.sigep.dominio.entidades.enums.EstadoPersona;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String celular;
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_persona")
    private EstadoPersona estadoPersona; // CANDIDATO, EMPLEADO

    @Column(name = "aspiracion_salarial")
    private BigDecimal aspiracionSalarial; // Cuánto quiere ganar

    // PARA EL PDF EN BASE64
    @Lob // Le dice a JPA: "Esto es un objeto grande"
    @Column(name = "hoja_vida_base64", columnDefinition = "LONGTEXT") 
    // Nota: 'columnDefinition="LONGTEXT"' es vital si usas MySQL/MariaDB. 
    // Si usas PostgreSQL o Oracle, solo con @Lob suele bastar o usa 'TEXT'.
    private String hojaVidaBase64;
    
    @Column(name = "fecha_postulacion")
    private LocalDate fechaPostulacion; // Para saber cuándo llegó el CV
    
 // Relación Muchos Candidatos -> Un Cargo
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_cargo_postulacion") // FK en la tabla personas
    private CargoEntity cargoPostulacion;
}