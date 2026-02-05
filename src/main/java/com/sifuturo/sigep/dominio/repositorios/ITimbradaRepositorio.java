package com.sifuturo.sigep.dominio.repositorios;

import java.util.List;
import com.sifuturo.sigep.dominio.entidades.Timbrada;

public interface ITimbradaRepositorio {
    Timbrada guardar(Timbrada timbrada);
    List<Timbrada> listarPorEmpleado(Long idEmpleado);
    Timbrada buscarUltimaPorEmpleado(Long idEmpleado); 
}