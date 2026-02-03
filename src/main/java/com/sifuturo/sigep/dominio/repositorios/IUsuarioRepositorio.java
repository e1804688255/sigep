package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Usuario;

public interface IUsuarioRepositorio {
	List<Usuario> listarTodos();

	List<Usuario> listarActivos();

	Optional<Usuario> buscarPorId(Long id);

	Usuario guardar(Usuario area);

	boolean existePorUsername(String usuario);

	Optional<Usuario>  buscarPorUsername(String usuario);
}