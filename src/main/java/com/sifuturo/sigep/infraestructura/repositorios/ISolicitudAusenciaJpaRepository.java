package com.sifuturo.sigep.infraestructura.repositorios;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.SolicitudAusenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ISolicitudAusenciaJpaRepository extends JpaRepository<SolicitudAusenciaEntity, Long> {
	// Spring Data crea la query automáticamente
	List<SolicitudAusenciaEntity> findByEmpleadoIdEmpleado(Long idEmpleado);

}