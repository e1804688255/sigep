package com.sifuturo.sigep.presentacion.mapeadores;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Archivo: com.sifuturo.sigep.presentacion.mapeadores.ISolicitudAusenciaDtoMapper.java
@Mapper(componentModel = "spring")
public interface ISolicitudAusenciaDtoMapper {

 @Mapping(target = "idEmpleado", source = "empleado.idEmpleado")
 @Mapping(target = "nombreEmpleado", expression = "java(concatenarNombre(entity))")
 SolicitudAusenciaDTO toDto(SolicitudAusencia entity);

 SolicitudAusencia toDomain(SolicitudAusenciaDTO dto);

 default String concatenarNombre(SolicitudAusencia entity) {
     if (entity.getEmpleado() == null || entity.getEmpleado().getPersona() == null) {
         return "Sin Nombre";
     }
     return entity.getEmpleado().getPersona().getNombres() + " " + 
            entity.getEmpleado().getPersona().getApellidos();
 }
}