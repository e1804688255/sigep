package com.sifuturo.sigep.presentacion.mapeadores;

import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.presentacion.dto.PersonaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // <--- Importante
import java.util.List;

@Mapper(componentModel = "spring")
public interface IPersonaDtoMapper {

    // DE DTO A DOMINIO (Cuando guardas)
    // Le decimos: "Toma el 'idCargoPostulacion' del DTO y mételo dentro de 'cargoPostulacion.id' en la entidad"
    @Mapping(target = "cargoPostulacion.id", source = "idCargoPostulacion")
    // Opcional: Ignoramos el mapeo del nombre al convertir a dominio porque no viene en el input o no nos sirve para guardar
    @Mapping(target = "cargoPostulacion.nombre", ignore = true) 
    Persona toDomain(PersonaDTO dto);

    // DE DOMINIO A DTO (Cuando listas)
    // Le decimos: "Saca el ID del objeto cargo y ponlo en la variable plana"
    @Mapping(target = "idCargoPostulacion", source = "cargoPostulacion.id")
    // Le decimos: "Saca el Nombre del cargo y ponlo para que se vea bonito en el front"
    @Mapping(target = "nombreCargoPostulacion", source = "cargoPostulacion.nombre")
    PersonaDTO toDto(Persona domain);

    List<PersonaDTO> toDtoList(List<Persona> domainList);
}