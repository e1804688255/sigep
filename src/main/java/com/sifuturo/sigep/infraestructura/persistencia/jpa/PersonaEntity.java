package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Data // <--- Ahora sí funcionará
@NoArgsConstructor
@AllArgsConstructor
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
}