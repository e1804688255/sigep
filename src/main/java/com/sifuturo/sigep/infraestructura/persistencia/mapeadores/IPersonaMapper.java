package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.InheritInverseConfiguration;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.PersonaEntity;

@Mapper(componentModel = "spring")
public interface IPersonaMapper {

	Persona toDomain(PersonaEntity entity);

	@InheritInverseConfiguration
	PersonaEntity toEntity(Persona persona);

	void updateEntityFromDomain(Persona domain, @MappingTarget PersonaEntity entity);
}