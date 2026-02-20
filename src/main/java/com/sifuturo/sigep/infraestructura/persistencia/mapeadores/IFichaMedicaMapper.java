package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.FichaMedicaEntity;
import com.sifuturo.sigep.presentacion.dto.response.FichaMedicaResponseDto;

@Mapper(componentModel = "spring", uses = { IEmpleadoMapper.class })
public interface IFichaMedicaMapper {

    FichaMedicaEntity toEntity(FichaMedica domain);

    FichaMedica toDomain(FichaMedicaEntity entity);

    // --- MAPEO PARA LA RESPUESTA JSON ---
    
    @Mapping(target = "idEmpleado", source = "empleado.idEmpleado")
    @Mapping(target = "cedulaEmpleado", source = "empleado.persona.cedula")
    // Usamos un método default para concatenar el nombre y evitar errores si es nulo
    @Mapping(target = "nombreEmpleado", source = "empleado", qualifiedByName = "mapNombreCompleto")
    FichaMedicaResponseDto toResponseDto(FichaMedica domain);

    @Named("mapNombreCompleto")
    default String mapNombreCompleto(Empleado empleado) {
        if (empleado == null || empleado.getPersona() == null) {
            return "Desconocido";
        }
        // Ajusta 'getNombre' o 'getNombres' según tu entidad Persona real
        return empleado.getPersona().getNombres() + " " + empleado.getPersona().getApellidos();
    }
}