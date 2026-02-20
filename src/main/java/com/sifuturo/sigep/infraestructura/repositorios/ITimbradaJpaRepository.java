package com.sifuturo.sigep.infraestructura.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sifuturo.sigep.infraestructura.persistencia.jpa.TimbradaEntity;

public interface ITimbradaJpaRepository extends JpaRepository<TimbradaEntity, Long> {

    // 1. Busca timbradas de un empleado
    List<TimbradaEntity> findByEmpleado_IdEmpleadoOrderByFechaHoraDesc(Long idEmpleado);
    
    // 2. Busca la última (para validaciones de entrada/salida)
    Optional<TimbradaEntity> findTopByEmpleado_IdEmpleadoOrderByFechaHoraDesc(Long idEmpleado);

    // --- 3. ESTE ES EL QUE FALTABA PARA TU PANTALLA DE "MIS TIMBRADAS" ---
    List<TimbradaEntity> findByEmpleado_IdEmpleadoAndFechaHoraBetweenOrderByFechaHoraDesc(
        Long idEmpleado, 
        LocalDateTime inicio, 
        LocalDateTime fin
    );

    // 4. Reporte General (Ya lo tenías bien)
    @Query("SELECT t FROM TimbradaEntity t " +
           "JOIN FETCH t.empleado e " +
           "WHERE t.fechaHora BETWEEN :inicio AND :fin " +
           "AND (:areaId IS NULL OR e.area.id = :areaId) " + 
           "AND t.estado = true " + 
           "ORDER BY t.fechaHora DESC")
    List<TimbradaEntity> buscarReporteGeneral(
        @Param("inicio") LocalDateTime inicio, 
        @Param("fin") LocalDateTime fin,
        @Param("areaId") Long areaId
    );
}