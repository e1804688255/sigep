package com.sifuturo.sigep.presentacion.mapeadores;

import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.presentacion.dto.FichaMedicaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IFichaMedicaDtoMapper {

    // De DTO a Dominio: Metemos el idPersona dentro del objeto Persona del dominio
    @Mapping(target = "persona.id", source = "idPersona")
    FichaMedica toDomain(FichaMedicaDTO dto);

    // De Dominio a DTO: Sacamos el ID para enviarlo al front
    @Mapping(target = "idPersona", source = "persona.id")
    FichaMedicaDTO toDto(FichaMedica domain);
}