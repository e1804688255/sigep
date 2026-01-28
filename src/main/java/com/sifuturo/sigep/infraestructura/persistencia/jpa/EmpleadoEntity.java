package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "empleados")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoEntity extends AuditoriaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empleado")
	private Long idEmpleado;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona", referencedColumnName = "id", nullable = false, unique = true)

	private PersonaEntity persona;

	// Relación Muchos Empleados -> Una Área
	@ManyToOne
	@JoinColumn(name = "id_area")
	private AreaEntity area;

	// Relación Muchos Empleados -> Un Cargo
	@ManyToOne
	@JoinColumn(name = "id_cargo")
	private CargoEntity cargo;

	@Column(name = "codigo_empleado", nullable = false, unique = true)
	private String codigoEmpleado;

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

}