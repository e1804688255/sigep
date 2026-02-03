package com.sifuturo.sigep.infraestructura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity;

@Repository
public interface ICargoJpaRepository extends JpaRepository<CargoEntity, Long> {
	List<CargoEntity> findByEstadoTrue();

	boolean existsByNombre(String nombre);
}