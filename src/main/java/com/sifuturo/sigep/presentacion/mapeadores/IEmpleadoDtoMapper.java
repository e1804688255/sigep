package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.presentacion.dto.EmpleadoDto;

@Mapper(componentModel = "spring")
public interface IEmpleadoDtoMapper {

	Empleado toDomain(EmpleadoDto dto);

	@InheritInverseConfiguration
	EmpleadoDto toEntity(Empleado domain);

	void updateEntityFromDomain(Empleado domain, @MappingTarget EmpleadoDto entity);

}
