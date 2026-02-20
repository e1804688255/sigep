package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.EmpleadoDto;

@Mapper(
    componentModel = "spring", 
    uses = {
        IPersonaDtoMapper.class, 
        IAreaDtoMapper.class, 
        ICargoDtoMapper.class
    }
)
public interface IEmpleadoDtoMapper {

    // --- DE DTO (Front) A DOMINIO (Back) ---
    
    // 1. Mapeamos el jefe (Long -> Objeto Empleado)
    @Mapping(target = "jefeInmediato", source = "idJefeInmediato", qualifiedByName = "mapJefeDesdeId")
    
    // 2. IMPORTANTÍSIMO: Mapeamos los IDs planos a Objetos para que el UseCase no falle
    @Mapping(target = "persona.id", source = "idPersona")
    @Mapping(target = "area.id", source = "idArea")
    @Mapping(target = "cargo.id", source = "idCargo")
    
    // 3. Los campos bancarios y de contrato se mapean solos porque tienen el mismo nombre
    Empleado toDomain(EmpleadoDto dto);


    // --- DE DOMINIO (Back) A DTO (Front) ---
    @InheritInverseConfiguration
    @Mapping(target = "idJefeInmediato", source = "jefeInmediato.idEmpleado")
    @Mapping(target = "idPersona", source = "persona.id")
    @Mapping(target = "idArea", source = "area.id")
    @Mapping(target = "idCargo", source = "cargo.id")
    EmpleadoDto toDto(Empleado domain); 

    void updateDtoFromDomain(Empleado domain, @MappingTarget EmpleadoDto dto);

    // Helper para convertir ID Long a Objeto Empleado (Jefe)
    @Named("mapJefeDesdeId")
    default Empleado mapJefeDesdeId(Long id) {
        if (id == null) return null;
        return Empleado.builder().idEmpleado(id).build();
    }
}