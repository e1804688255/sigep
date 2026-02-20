package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;

public interface IFichaMedicaRepositorio {
    FichaMedica guardar(FichaMedica ficha);
    List<FichaMedica> listarPorEmpleado(Long idEmpleado);
}