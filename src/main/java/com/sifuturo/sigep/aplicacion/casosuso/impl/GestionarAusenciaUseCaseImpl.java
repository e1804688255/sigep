package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IGestionarAusenciaUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.SolicitudAusencia;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoSolicitud;
import com.sifuturo.sigep.dominio.entidades.enums.TipoAusencia; // Importante
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ISolicitudAusenciaRepositorio;
import com.sifuturo.sigep.presentacion.dto.SolicitudAusenciaDTO;
import com.sifuturo.sigep.presentacion.mapeadores.ISolicitudAusenciaDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GestionarAusenciaUseCaseImpl implements IGestionarAusenciaUseCase {

    private final ISolicitudAusenciaRepositorio repositorio;
    private final IEmpleadoRepositorio empleadoRepositorio; // <--- Inyecta esto
    private final ISolicitudAusenciaDtoMapper mapper;
    
    @Override
    public SolicitudAusenciaDTO crearSolicitud(SolicitudAusenciaDTO dto) {
        // 1. Convertir DTO a Dominio
        SolicitudAusencia solicitud = mapper.toDomain(dto);
        
        // 2. Asignar valores automáticos
        solicitud.setFechaSolicitud(LocalDateTime.now());
        
        // CORRECCIÓN: Usamos 'setEstadoSolicitud' (el nombre nuevo)
        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_APROBACION_JEFE); 
        
        // 3. Validación de Negocio
        // Usamos el ENUM directamente, es más seguro que comparar strings ("CITA_MEDICA")
        if (solicitud.getTipo() == TipoAusencia.CITA_MEDICA) {
             if (solicitud.getEvidenciaBase64() == null || solicitud.getEvidenciaBase64().isEmpty()) {
                 throw new RuntimeException("Para Citas Médicas es obligatorio adjuntar el certificado/evidencia.");
             }
        }

        // 4. Guardar y devolver
        return mapper.toDto(repositorio.guardar(solicitud));
    }

    @Override
    public SolicitudAusenciaDTO aprobarPorJefe(Long idSolicitud) {
        SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // CORRECCIÓN: 'getEstadoSolicitud'
        if (solicitud.getEstadoSolicitud() != EstadoSolicitud.PENDIENTE_APROBACION_JEFE) {
            throw new RuntimeException("Esta solicitud no está pendiente de aprobación por el jefe.");
        }

        // Cambio de estado
        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_AUTORIZACION_TH);
        
        return mapper.toDto(repositorio.guardar(solicitud));
    }

    @Override
    public SolicitudAusenciaDTO aprobarPorTH(Long idSolicitud) {
        SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // CORRECCIÓN: 'getEstadoSolicitud'
        if (solicitud.getEstadoSolicitud() != EstadoSolicitud.PENDIENTE_AUTORIZACION_TH) {
            throw new RuntimeException("La solicitud debe ser aprobada por el jefe antes de llegar a Talento Humano.");
        }

        // Aprobación final
        solicitud.setEstadoSolicitud(EstadoSolicitud.APROBADO);
        
        // AQUÍ LÓGICA FUTURA: Descontar días si es Vacaciones
        // if (solicitud.getTipo() == TipoAusencia.VACACIONES) { ... }

        return mapper.toDto(repositorio.guardar(solicitud));
    }

      
    @Override
    public List<SolicitudAusenciaDTO> listarPorEmpleado(Long idEmpleado) {
        // 1. Llamar al repositorio (Dominio)
        List<SolicitudAusencia> lista = repositorio.listarPorEmpleado(idEmpleado);

        // 2. Mapear Lista de Entidades a Lista de DTOs
        // Si tu mapper tiene un método toDtoList, úsalo. Si no, usa este stream:
        return lista.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<SolicitudAusenciaDTO> listarTodas() {
        return repositorio.listarTodas().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SolicitudAusenciaDTO rechazar(Long idSolicitud, String motivoRechazo) {
        SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstadoSolicitud(EstadoSolicitud.RECHAZADO_TH); 
        solicitud.setObservaciones(motivoRechazo); // Guardamos el motivo en el campo nuevo

        return mapper.toDto(repositorio.guardar(solicitud));
    }
    


        @Override
        public SolicitudAusenciaDTO procesarAprobacion(Long idSolicitud, Long idEjecutor) {
            SolicitudAusencia solicitud = repositorio.obtenerPorId(idSolicitud)
                    .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
                    
            // Buscamos al empleado que está haciendo clic en "Aprobar"
            Empleado ejecutor = empleadoRepositorio.buscarPorId(idEjecutor)
                    .orElseThrow(() -> new RuntimeException("Empleado ejecutor no existe"));

            // Lógica de Negocio según tus tablas:
            boolean esJefe = ejecutor.getCargo().getId() == 1; // Jefe de área
            boolean esEspecialistaTH = (ejecutor.getArea().getId() == 2 && ejecutor.getCargo().getId() == 2); // TH Especialista

            if (esJefe && solicitud.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE_APROBACION_JEFE) {
                solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE_AUTORIZACION_TH);
            } 
            else if (esEspecialistaTH && solicitud.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE_AUTORIZACION_TH) {
                solicitud.setEstadoSolicitud(EstadoSolicitud.APROBADO);
            } 
            else {
                throw new RuntimeException("No tienes el rol necesario para aprobar en este estado.");
            }

            return mapper.toDto(repositorio.guardar(solicitud));
        }
}