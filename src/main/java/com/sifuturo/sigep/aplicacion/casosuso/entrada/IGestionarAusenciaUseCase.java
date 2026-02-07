package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;

import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;

public interface IGestionarAusenciaUseCase {
	SolicitudAusenciaDTO crearSolicitud(SolicitudAusenciaDTO dto);

	SolicitudAusenciaDTO aprobarPorJefe(Long idSolicitud);

	SolicitudAusenciaDTO aprobarPorTH(Long idSolicitud);

	SolicitudAusenciaDTO rechazar(Long idSolicitud, String motivoRechazo);
	List<SolicitudAusenciaDTO> listarPorEmpleado(Long idEmpleado);
	
	
	List<SolicitudAusenciaDTO> listarTodas(); 
	SolicitudAusenciaDTO procesarAprobacion(Long idSolicitud, Long idEjecutor);
}