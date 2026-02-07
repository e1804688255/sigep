package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.SolicitudAusenciaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

// 'uses' es vital para que sepa cómo transformar Empleado <-> EmpleadoEntity
@Mapper(componentModel = "spring", uses = {IEmpleadoMapper.class}) 
public interface ISolicitudAusenciaEntityMapper {

    SolicitudAusencia toDomain(SolicitudAusenciaEntity entity);

    @InheritInverseConfiguration
    SolicitudAusenciaEntity toEntity(SolicitudAusencia domain);
}