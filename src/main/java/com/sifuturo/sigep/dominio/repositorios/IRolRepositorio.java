package com.sifuturo.sigep.dominio.repositorios;

import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.Rol;

public interface IRolRepositorio {
	Optional<Rol> buscarPorId(Long id);

}
