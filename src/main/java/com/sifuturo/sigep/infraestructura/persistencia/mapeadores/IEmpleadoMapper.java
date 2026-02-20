package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.EmpleadoEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IEmpleadoMapper {

    // Los campos banco, tipoCuentaBancaria, etc., se mapean automáticamente
    // porque se llaman igual en Empleado y EmpleadoEntity.

    @Mapping(target = "jefeInmediato", source = "jefeInmediato") 
    Empleado toDomain(EmpleadoEntity entity);

    @InheritInverseConfiguration
    EmpleadoEntity toEntity(Empleado empleado);

    void updateEntityFromDomain(Empleado domain, @MappingTarget EmpleadoEntity entity);
}