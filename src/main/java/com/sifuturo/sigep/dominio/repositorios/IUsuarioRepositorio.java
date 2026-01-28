package com.sifuturo.sigep.dominio.repositorios;

import java.util.Optional;
import com.sifuturo.sigep.dominio.entidades.Usuario;

public interface IUsuarioRepositorio {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorUsername(String username);
    boolean existePorUsername(String username);
}