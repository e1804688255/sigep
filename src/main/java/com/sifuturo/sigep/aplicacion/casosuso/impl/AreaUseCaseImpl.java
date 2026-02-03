package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante usar el de Spring

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IAreaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.util.AppUtil;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.repositorios.IAreaRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaUseCaseImpl implements IAreaUseCase {

    private final IAreaRepositorio areaRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<Area> listarActivos() {
        return areaRepositorio.listarActivos();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Area> listarTodos() {
        return areaRepositorio.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Area> buscarPorId(Long id) {
        return areaRepositorio.buscarPorId(id);
    }

    @Override
    @Transactional
    public Area guardar(Area area) {
        // Regla de Negocio: No permitir nombres duplicados al crear
        if (areaRepositorio.existePorNombre(area.getNombre())) {
            throw new RuntimeException("Ya existe un área con el nombre: " + area.getNombre());
        }
        // Por defecto al crear el estado es true (lo maneja EntidadBase), pero aseguramos:
        area.setEstado(true); 
        return areaRepositorio.guardar(area);
    }

    @Override
    @Transactional
    public Area actualizar(Long id, Area areaEntrada) {
		Area areaDb = areaRepositorio.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));

		AppUtil.copiarPropiedadesNoNulas(areaEntrada, areaDb);
		
		return areaRepositorio.guardar(areaDb);
	}

    @Override
    @Transactional
    public void eliminar(Long id) {
        Area area = areaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Area no encontrada con ID: " + id));
        
        // Eliminación Lógica
        area.setEstado(false); 
        areaRepositorio.guardar(area);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return areaRepositorio.existePorNombre(nombre);
    }
}