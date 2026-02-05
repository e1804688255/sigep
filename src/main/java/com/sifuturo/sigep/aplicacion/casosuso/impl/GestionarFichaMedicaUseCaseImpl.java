package com.sifuturo.sigep.aplicacion.casosuso.impl;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IGestionarFichaMedicaUseCase;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.dominio.repositorios.IFichaMedicaRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio; // Necesario para validar existencia
import com.sifuturo.sigep.presentacion.dto.FichaMedicaDTO;
import com.sifuturo.sigep.presentacion.mapeadores.IFichaMedicaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestionarFichaMedicaUseCaseImpl implements IGestionarFichaMedicaUseCase {

    private final IFichaMedicaRepositorio fichaRepositorio;
    private final IPersonaRepositorio personaRepositorio;
    private final IFichaMedicaDtoMapper dtoMapper;

    @Override
    public FichaMedicaDTO guardarFicha(FichaMedicaDTO dto) {
        // 1. Convertir DTO -> Dominio
        FichaMedica fichaDominio = dtoMapper.toDomain(dto);

        // 2. Lógica de Negocio: Verificar que la persona existe
        Long idPersona = fichaDominio.getPersona().getId();
        if (!personaRepositorio.existePorId(idPersona)) { // Asumo que tienes un método similar
             throw new RuntimeException("La persona no existe");
        }

        // 3. Lógica: Si ya existe ficha, actualizarla (mantener el ID)
        fichaRepositorio.buscarPorIdPersona(idPersona).ifPresent(fichaExistente -> {
            fichaDominio.setId(fichaExistente.getId());
        });

        // 4. Guardar (Dominio pasa al Repositorio)
        FichaMedica fichaGuardada = fichaRepositorio.guardar(fichaDominio);

        // 5. Convertir Dominio -> DTO para responder
        return dtoMapper.toDto(fichaGuardada);
    }
}