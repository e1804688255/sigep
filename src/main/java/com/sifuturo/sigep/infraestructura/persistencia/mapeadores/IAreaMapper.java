package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.AreaEntity;

@Mapper(componentModel = "spring")
public interface IAreaMapper {
    AreaEntity toEntity(Area domain);

    @InheritInverseConfiguration
    Area toDomain(AreaEntity entity);
}