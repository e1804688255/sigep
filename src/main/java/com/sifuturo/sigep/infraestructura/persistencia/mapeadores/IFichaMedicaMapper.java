package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;


@Mapper(componentModel = "spring", uses = {IPersonaMapper.class}) 
public interface IFichaMedicaMapper {

    FichaMedica toDomain(FichaMedicaEntity entity);

    @InheritInverseConfiguration
    FichaMedicaEntity toEntity(FichaMedica domain);
}