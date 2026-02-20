package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;

public interface IFichaMedicaJpaRepository extends JpaRepository<FichaMedicaEntity, Long> {
    List<FichaMedicaEntity> findByEmpleado_IdEmpleadoOrderByFechaConsultaDesc(Long idEmpleado);
}