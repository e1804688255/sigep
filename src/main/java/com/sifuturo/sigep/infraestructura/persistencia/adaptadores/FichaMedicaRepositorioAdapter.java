package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.dominio.repositorios.IFichaMedicaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IFichaMedicaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IFichaMedicaJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FichaMedicaRepositorioAdapter implements IFichaMedicaRepositorio {

    private final IFichaMedicaJpaRepository jpaRepository;
    private final IFichaMedicaMapper mapper;

    @Override
    public FichaMedica guardar(FichaMedica ficha) {
        FichaMedicaEntity entity = mapper.toEntity(ficha);
        if(entity.getEstado() == null) entity.setEstado(true);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<FichaMedica> listarPorEmpleado(Long idEmpleado) {
        return jpaRepository.findByEmpleado_IdEmpleadoOrderByFechaConsultaDesc(idEmpleado)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}