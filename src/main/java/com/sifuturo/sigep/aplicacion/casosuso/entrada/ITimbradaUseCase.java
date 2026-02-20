package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.time.LocalDateTime;
import java.util.List;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;
import com.sifuturo.sigep.presentacion.dto.response.ReporteAsistenciaConsolidadoDto;

public interface ITimbradaUseCase {
    Timbrada registrar(RegistrarTimbradaDto dto);
    List<Timbrada> listarMisTimbradas(Long idEmpleado);
    List<Timbrada> listarMisTimbradasConFiltro(Long idEmpleado, LocalDateTime inicio, LocalDateTime fin);
    
    List<Timbrada> listarReporteGeneral(LocalDateTime inicio, LocalDateTime fin, Long areaId);
    List<ReporteAsistenciaConsolidadoDto> generarReporteCalculado(LocalDateTime inicio, LocalDateTime fin, Long areaId);
}