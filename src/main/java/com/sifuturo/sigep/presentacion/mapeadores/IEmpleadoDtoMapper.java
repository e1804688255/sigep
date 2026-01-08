package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.presentacion.dto.EmpleadoDto;

@Mapper(componentModel = "spring", uses = {IPersonaDtoMapper.class})
public interface IEmpleadoDtoMapper {

    Empleado toDomain(EmpleadoDto dto);

    @InheritInverseConfiguration
    EmpleadoDto toDto(Empleado domain); 

    void updateDtoFromDomain(Empleado domain, @MappingTarget EmpleadoDto dto);
}