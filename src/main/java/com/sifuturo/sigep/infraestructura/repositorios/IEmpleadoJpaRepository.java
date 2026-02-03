package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.EmpleadoEntity;

@Repository
public interface IEmpleadoJpaRepository extends JpaRepository<EmpleadoEntity, Long> {
	Optional<EmpleadoEntity> findByCodigoEmpleado(String codigoEmpleado);

	List<EmpleadoEntity> findByEstadoTrue();

	boolean existsByNombre(String nombre);
}
