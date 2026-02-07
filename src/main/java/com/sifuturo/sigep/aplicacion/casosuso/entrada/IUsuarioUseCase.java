package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import java.util.Optional;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.presentacion.dto.CrearUsuarioDto;

public interface IUsuarioUseCase {

	List<Usuario> listarTodos();

	List<Usuario> listarActivos();

	Optional<Usuario> buscarPorId(Long id);

	boolean existePorUsername(String usuario);

	Usuario actualizar(Long id, Usuario usuario);

	void eliminar(Long id);

	Usuario registrarUsuario(CrearUsuarioDto crearUsuarioDto);

	void resetearPassword(Long id, String nuevaClave); 
	
	void cambiarEstado(Long id, boolean nuevoEstado);
}
