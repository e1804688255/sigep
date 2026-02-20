package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;

@Mapper(componentModel = "spring")
public interface ISolicitudAusenciaDtoMapper {

    // 1. De DOMINIO a DTO
    @Mapping(target = "idEmpleado", source = "empleado.idEmpleado")
    @Mapping(target = "nombreEmpleado", expression = "java(concatenarNombre(entity))")
    SolicitudAusenciaDTO toDto(SolicitudAusencia entity);

    // 2. De DTO a DOMINIO (Aquí aplicamos la Opción 1)
    // Le decimos: "Toma el idEmpleado del DTO y usa el método 'mapEmpleadoDesdeId' para llenar el objeto 'empleado'"
    @Mapping(target = "empleado", source = "idEmpleado", qualifiedByName = "mapEmpleadoDesdeId") 
    @Mapping(target = "estadoSolicitud", ignore = true) // El estado lo manejamos en el caso de uso, no en el DTO de entrada
    SolicitudAusencia toDomain(SolicitudAusenciaDTO dto);

    // 3. Método auxiliar para concatenar nombres (Tu código original)
    default String concatenarNombre(SolicitudAusencia entity) {
        if (entity.getEmpleado() == null || entity.getEmpleado().getPersona() == null) {
            return "Sin Nombre";
        }
        return entity.getEmpleado().getPersona().getNombres() + " " + 
               entity.getEmpleado().getPersona().getApellidos();
    }

    // 4. EL MÉTODO MÁGICO (Igual que hicimos con el Jefe)
    @Named("mapEmpleadoDesdeId")
    default Empleado mapEmpleadoDesdeId(Long id) {
        if (id == null) return null;
        return Empleado.builder().idEmpleado(id).build();
    }
}