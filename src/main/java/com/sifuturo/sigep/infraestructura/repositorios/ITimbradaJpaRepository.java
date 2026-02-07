package com.sifuturo.sigep.infraestructura.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.infraestructura.persistencia.jpa.TimbradaEntity;

@Repository
public interface ITimbradaJpaRepository extends JpaRepository<TimbradaEntity, Long> {

	@Query("SELECT t FROM TimbradaEntity t WHERE t.empleado.idEmpleado = :idEmpleado ORDER BY t.fechaHora DESC")
	List<TimbradaEntity> findByEmpleadoIdOrderByFechaHoraDesc(@Param("idEmpleado") Long idEmpleado);

	Optional<TimbradaEntity> findTopByEmpleado_IdEmpleadoOrderByFechaHoraDesc(Long idEmpleado);

	@Query("SELECT t FROM TimbradaEntity t WHERE t.empleado.idEmpleado = :idEmpleado "
			+ "AND t.fechaHora BETWEEN :inicio AND :fin ORDER BY t.fechaHora DESC")
	List<TimbradaEntity> findByEmpleadoAndFecha(@Param("idEmpleado") Long idEmpleado,
			@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	List<TimbradaEntity> findByEmpleadoIdEmpleadoOrderByFechaHoraDesc(Long idEmpleado);
}