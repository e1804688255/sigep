package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.Optional;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;
import org.springframework.data.jpa.repository.JpaRepository; // IMPORTANTE

public interface IFichaMedicaJpaRepository extends JpaRepository<FichaMedicaEntity, Long> {

    Optional<FichaMedicaEntity> findByPersonaId(Long idPersona);
}