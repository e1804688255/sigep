package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.AreaEntity;

@Repository
public interface IAreaJpaRepository extends JpaRepository<AreaEntity, Long> {
    
    List<AreaEntity> findByEstadoTrue();
    
    boolean existsByNombre(String nombre);
}