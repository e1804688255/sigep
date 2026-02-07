package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Mapper(componentModel = "spring", uses = {ICargoMapper.class})
public interface IPersonaMapper {

    // 1. Configuración principal: Ignoramos auditoría al crear
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "usuarioCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "usuarioModificacion", ignore = true)
    PersonaEntity toEntity(Persona domain);

    // 2. SOLUCIÓN AL ERROR: Le decimos explícitamente que invierta 'toEntity'
    @InheritInverseConfiguration(name = "toEntity")
    Persona toDomain(PersonaEntity entity);

    // 3. RECOMENDACIÓN: Copiar las reglas de ignorar también al actualizar
    // Si no pones esto, al actualizar una persona, MapStruct podría poner NULL en fechaCreacion
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "usuarioCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true) // Dejamos que JPA maneje la fecha de modificación
    @Mapping(target = "usuarioModificacion", ignore = true)
    void updateEntityFromDomain(Persona domain, @MappingTarget PersonaEntity entity);
}