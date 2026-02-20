package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Mapper(componentModel = "spring", uses = {ICargoMapper.class})
public interface IPersonaMapper {

    PersonaEntity toEntity(Persona domain);

    Persona toDomain(PersonaEntity entity);

    // ESTE MÉTODO ES LA CLAVE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true) // <--- ¡AQUÍ! Evitamos que MapStruct ponga NULL el estado
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "usuarioCreacion", ignore = true)
    void updateEntityFromDomain(Persona domain, @MappingTarget PersonaEntity entity);
}