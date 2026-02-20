package com.sifuturo.sigep.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.UsuarioEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IUsuarioMapper {
	@Mapping(source = "id", target = "idUsuario")
	Usuario toDomain(UsuarioEntity entity);

	@Mapping(source = "idUsuario", target = "id")
	UsuarioEntity toEntity(Usuario domain);
}