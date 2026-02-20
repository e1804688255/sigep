package com.sifuturo.sigep.infraestructura.repositorios;

import com.sifuturo.sigep.infraestructura.persistencia.jpa.SolicitudAusenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ISolicitudAusenciaJpaRepository extends JpaRepository<SolicitudAusenciaEntity, Long> {
	// Spring Data crea la query automáticamente
	List<SolicitudAusenciaEntity> findByEmpleadoIdEmpleado(Long idEmpleado);

	@Query("SELECT s FROM SolicitudAusenciaEntity s " + "WHERE s.empleado.id = :idEmpleado "
			+ "AND s.estadoSolicitud = 'APROBADO' " + "AND s.fechaInicio <= :fin " + "AND s.fechaFin >= :inicio")
	List<SolicitudAusenciaEntity> findApprovedByEmpleadoAndRange(@Param("idEmpleado") Long idEmpleado,
			@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}