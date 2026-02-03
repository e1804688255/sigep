package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.UsuarioEntity;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
	Optional<UsuarioEntity> findByUsername(String username);

	boolean existsByUsername(String username);

	List<UsuarioEntity> findByEstadoTrue();

}