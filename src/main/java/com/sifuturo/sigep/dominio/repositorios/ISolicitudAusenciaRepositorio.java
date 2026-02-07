package com.sifuturo.sigep.dominio.repositorios;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import java.util.Optional;
import java.util.List;

public interface ISolicitudAusenciaRepositorio {
    SolicitudAusencia guardar(SolicitudAusencia solicitud);
    Optional<SolicitudAusencia> obtenerPorId(Long id);
    // Útil para listar las solicitudes de un empleado específico
    List<SolicitudAusencia> listarPorEmpleado(Long idEmpleado);
    List<SolicitudAusencia> listarTodas(); // <-- Agregar aquí
    }