package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Rol;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.RolEntity;

@Mapper(componentModel = "spring")
public interface IRolMapper {
    Rol toDomain(RolEntity entity);

    @InheritInverseConfiguration
    RolEntity toEntity(Rol domain);
}