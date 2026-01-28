package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.EmpleadoEntity;

@Mapper(
    componentModel = "spring", 
    uses = {IPersonaMapper.class, IAreaMapper.class, ICargoMapper.class}
)
public interface IEmpleadoMapper {

    Empleado toDomain(EmpleadoEntity entity);

    @InheritInverseConfiguration
    EmpleadoEntity toEntity(Empleado empleado);

    void updateEntityFromDomain(Empleado domain, @MappingTarget EmpleadoEntity entity);
}