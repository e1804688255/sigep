package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.presentacion.dto.CargoDto;

@Mapper(componentModel = "spring")
public interface ICargoDtoMapper {
	CargoDto toDto(Cargo domain);

	@InheritInverseConfiguration
	Cargo toDomain(CargoDto dto);
}