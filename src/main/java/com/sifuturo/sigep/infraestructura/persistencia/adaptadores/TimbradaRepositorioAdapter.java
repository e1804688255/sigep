package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.dominio.repositorios.ITimbradaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.TimbradaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.ITimbradaMapper; // Importa tu interfaz
import com.sifuturo.sigep.infraestructura.repositorios.ITimbradaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimbradaRepositorioAdapter implements ITimbradaRepositorio {

    private final ITimbradaJpaRepository jpaRepository;
    private final ITimbradaMapper mapper; // Usamos la Interfaz, no la clase

    @Override
    public List<Timbrada> listarPorEmpleadoYRango(Long idEmpleado, LocalDateTime inicio, LocalDateTime fin) {
        return jpaRepository.findByEmpleadoAndFecha(idEmpleado, inicio, fin)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Timbrada guardar(Timbrada timbrada) {
        // 1. Convertir Dominio -> Entidad usando MapStruct
        TimbradaEntity entity = mapper.toEntity(timbrada);

        // 2. Guardar en BD
        TimbradaEntity guardado = jpaRepository.save(entity);

        // 3. Convertir Entidad guardada -> Dominio y retornar
        return mapper.toDomain(guardado);
    }

    @Override
    public List<Timbrada> listarPorEmpleado(Long idEmpleado) {
        // Buscamos las entidades y las transformamos con Stream y MapStruct
        return jpaRepository.findByEmpleadoIdOrderByFechaHoraDesc(idEmpleado)
                .stream()
                .map(mapper::toDomain) // Referencia al método del mapper
                .collect(Collectors.toList());
    }

    @Override
    public Timbrada buscarUltimaPorEmpleado(Long idEmpleado) {
        // Usamos el método findTop del repositorio JPA
    	return jpaRepository.findTopByEmpleado_IdEmpleadoOrderByFechaHoraDesc(idEmpleado)
                .map(mapper::toDomain)
                .orElse(null);
    }
}