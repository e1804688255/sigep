package com.sifuturo.sigep.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.AreaEntity;

@Repository
public interface IAreaJpaRepository extends JpaRepository<AreaEntity, Long> {
    // JpaRepository ya incluye findById, save, delete, etc.
}