package com.sifuturo.sigep.infraestructura.persistencia.jpa;

import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_ausencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SolicitudAusenciaEntity extends AuditoriaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    
    @Column(length = 500)
    private String motivo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String evidenciaBase64;

    @Enumerated(EnumType.STRING)
    private TipoAusencia tipo;

    // --- CAMBIO AQUÍ: Renombramos a 'estadoSolicitud' ---
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_solicitud")
    private EstadoSolicitud estadoSolicitud; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntity empleado; 
    
    @Column(length = 500)
    private String observaciones; 
}