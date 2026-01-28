package com.sifuturo.sigep.infraestructura.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.RolEntity;

@Repository
public interface IRolJpaRepository extends JpaRepository<RolEntity, Long> {
}
