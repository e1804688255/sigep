package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.presentacion.dto.AreaDto;

@Mapper(componentModel = "spring")
public interface IAreaDtoMapper {
    AreaDto toDto(Area domain);
    
    Area toDomain(AreaDto dto);
}