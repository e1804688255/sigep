package com.sifuturo.sigep.aplicacion.casosuso.entrada;


import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;

public interface IUsuarioUseCase {
	Usuario registrarUsuario(CrearUsuarioDto crearUsuarioDto);
	
}
