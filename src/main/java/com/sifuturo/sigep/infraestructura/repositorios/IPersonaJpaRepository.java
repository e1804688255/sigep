package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Repository
public interface IPersonaJpaRepository extends JpaRepository<PersonaEntity, Long> {
    // Spring Data JPA implementa esto automágicamente
    Optional<PersonaEntity> findByCedula(String cedula);
}