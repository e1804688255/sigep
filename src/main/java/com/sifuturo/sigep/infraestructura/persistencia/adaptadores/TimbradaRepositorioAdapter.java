package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.dominio.repositorios.ITimbradaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.TimbradaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.ITimbradaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.ITimbradaJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimbradaRepositorioAdapter implements ITimbradaRepositorio {

    private final ITimbradaJpaRepository jpaRepository;
    private final ITimbradaMapper mapper;

    @Override
    public List<Timbrada> listarPorEmpleadoYRango(Long idEmpleado, LocalDateTime inicio, LocalDateTime fin) {
        // --- CORRECCIÓN: YA NO RETORNA NULL ---
        return jpaRepository.findByEmpleado_IdEmpleadoAndFechaHoraBetweenOrderByFechaHoraDesc(idEmpleado, inicio, fin)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Timbrada> listarTodasEnRango(LocalDateTime inicio, LocalDateTime fin, Long areaId) {
        return jpaRepository.buscarReporteGeneral(inicio, fin, areaId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Timbrada guardar(Timbrada timbrada) {
        TimbradaEntity entity = mapper.toEntity(timbrada);
        
        // --- BLINDAJE EXTRA PARA EL ESTADO ---
        // Si por alguna razón el dominio viene sin estado, lo forzamos a true aquí antes de guardar
        if (entity.getEstado() == null) {
            entity.setEstado(true);
        }

        TimbradaEntity guardado = jpaRepository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public List<Timbrada> listarPorEmpleado(Long idEmpleado) {
        return jpaRepository.findByEmpleado_IdEmpleadoOrderByFechaHoraDesc(idEmpleado)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Timbrada buscarUltimaPorEmpleado(Long idEmpleado) {
        return jpaRepository.findTopByEmpleado_IdEmpleadoOrderByFechaHoraDesc(idEmpleado)
                .map(mapper::toDomain)
                .orElse(null);
    }
}