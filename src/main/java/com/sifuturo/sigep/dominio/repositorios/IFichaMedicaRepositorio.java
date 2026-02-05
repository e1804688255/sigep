package com.sifuturo.sigep.dominio.repositorios;

import java.util.Optional;

import com.sifuturo.sigep.dominio.entidades.FichaMedica;

public interface IFichaMedicaRepositorio {
	FichaMedica guardar(FichaMedica ficha);
    Optional<FichaMedica> buscarPorIdPersona(Long idPersona);
}
