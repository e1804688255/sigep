package com.sifuturo.sigep.presentacion.mapeadores;

import org.mapstruct.Mapper;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.response.UsuarioResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUsuarioPresentacionMapper {
    
    // Convierte un Usuario de dominio a DTO de respuesta
    UsuarioResponseDto toResponseDto(Usuario usuario);

    // Convierte la lista completa para el GetMapping
    List<UsuarioResponseDto> toResponseDtoList(List<Usuario> usuarios);
}