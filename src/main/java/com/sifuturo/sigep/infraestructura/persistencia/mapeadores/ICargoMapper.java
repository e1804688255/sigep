package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity;

@Mapper(componentModel = "spring")
public interface ICargoMapper {
    CargoEntity toEntity(Cargo domain);

    @InheritInverseConfiguration
    Cargo toDomain(CargoEntity entity);
}