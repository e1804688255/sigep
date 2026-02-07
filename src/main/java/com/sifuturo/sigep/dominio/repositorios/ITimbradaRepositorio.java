package com.sifuturo.sigep.dominio.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import com.sifuturo.sigep.dominio.entidades.Timbrada;

public interface ITimbradaRepositorio {
    Timbrada guardar(Timbrada timbrada);
    List<Timbrada> listarPorEmpleado(Long idEmpleado);
    Timbrada buscarUltimaPorEmpleado(Long idEmpleado); 
    List<Timbrada> listarPorEmpleadoYRango(Long idEmpleado, LocalDateTime inicio, LocalDateTime fin);
}