package com.sifuturo.sigep.presentacion.mapeadores;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IPersonaDtoMapper {

	Persona toDomain(PersonaDTO dto);

	PersonaDTO toDto(Persona domain);

	List<PersonaDTO> toDtoList(List<Persona> domainList);
}