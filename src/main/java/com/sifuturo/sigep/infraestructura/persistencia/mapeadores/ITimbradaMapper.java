package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.TimbradaEntity;

@Mapper(componentModel = "spring", uses = {IEmpleadoMapper.class}) // "uses" si tienes mapper de empleado
public interface ITimbradaMapper {
	  TimbradaEntity toEntity(Timbrada domain);

	    @InheritInverseConfiguration
	    Timbrada toDomain(TimbradaEntity entity);
}
