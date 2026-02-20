package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IFichaMedicaUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IFichaMedicaRepositorio;
import com.sifuturo.sigep.presentacion.dto.RegistrarFichaDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FichaMedicaUseCaseImpl implements IFichaMedicaUseCase {

    private final IFichaMedicaRepositorio fichaRepositorio;
    private final IEmpleadoRepositorio empleadoRepositorio;

    @Override
    @Transactional
    public FichaMedica registrarConsulta(RegistrarFichaDto dto) {
        Empleado empleado = empleadoRepositorio.buscarPorId(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + dto.getIdEmpleado()));

        FichaMedica ficha = FichaMedica.builder()
                .empleado(empleado)
                .fechaConsulta(dto.getFechaConsulta() != null ? dto.getFechaConsulta() : LocalDateTime.now())
                .motivo(dto.getMotivo())
                .diagnostico(dto.getDiagnostico())
                .tratamiento(dto.getTratamiento())
                .doctorNombre(dto.getDoctorNombre())
                // --- MAPEO DE LOS NUEVOS CAMPOS ---
                .presion(dto.getPresion())
                .temperatura(dto.getTemperatura())
                .peso(dto.getPeso())
                .build();
        
        ficha.setEstado(true);
        return fichaRepositorio.guardar(ficha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichaMedica> obtenerHistorialMedico(Long idEmpleado) {
        return fichaRepositorio.listarPorEmpleado(idEmpleado);
    }
}