package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.dominio.repositorios.IFichaMedicaRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IFichaMedicaMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IFichaMedicaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FichaMedicaRepositorioAdapter implements IFichaMedicaRepositorio {

    private final IFichaMedicaJpaRepository jpaRepository;
    private final IFichaMedicaMapper mapper;

    @Override
    public FichaMedica guardar(FichaMedica ficha) {
        FichaMedicaEntity entity = mapper.toEntity(ficha);
        
        // CORREGIDO: Usamos .save() que es el método nativo de JpaRepository
        FichaMedicaEntity guardado = jpaRepository.save(entity);
        
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<FichaMedica> buscarPorIdPersona(Long idPersona) {
        // CORREGIDO: Usamos el método en inglés que definimos en la interfaz JPA
        return jpaRepository.findByPersonaId(idPersona)
                .map(mapper::toDomain);
    }
}