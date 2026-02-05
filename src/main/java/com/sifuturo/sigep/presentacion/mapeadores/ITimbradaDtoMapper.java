package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;

@Mapper(componentModel = "spring")
public interface ITimbradaDtoMapper {
	 RegistrarTimbradaDto toDto(RegistrarTimbradaDto domain);
	    
	 RegistrarTimbradaDto toDomain(RegistrarTimbradaDto dto);
}

