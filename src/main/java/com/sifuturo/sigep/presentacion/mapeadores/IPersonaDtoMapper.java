package com.sifuturo.sigep.presentacion.mapeadores;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IPersonaDtoMapper {

    // DE DTO A DOMINIO (Entrada)
    @Mapping(target = "cargoPostulacion.id", source = "idCargoPostulacion")
    @Mapping(target = "cargoPostulacion.nombre", ignore = true) 
    Persona toDomain(PersonaDTO dto);

    // DE DOMINIO A DTO (Salida)
    @Mapping(target = "idCargoPostulacion", source = "cargoPostulacion.id")
    @Mapping(target = "nombreCargoPostulacion", source = "cargoPostulacion.nombre")
    PersonaDTO toDto(Persona domain);

    List<PersonaDTO> toDtoList(List<Persona> domainList);
}