package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.UsuarioEntity;

// 'uses' es clave para mapear el Empleado dentro del Usuario
@Mapper(componentModel = "spring", uses = {IEmpleadoMapper.class})
public interface IUsuarioMapper {

    @Mapping(source = "id", target = "idUsuario")
    Usuario toDomain(UsuarioEntity entity);

    @InheritInverseConfiguration
    UsuarioEntity toEntity(Usuario domain);
}