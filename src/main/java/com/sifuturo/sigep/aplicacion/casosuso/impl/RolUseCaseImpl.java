package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IRolUseCase;
import com.sifuturo.sigep.aplicacion.util.AppUtil;
import com.sifuturo.sigep.dominio.entidades.Rol;
import com.sifuturo.sigep.dominio.repositorios.IRolRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolUseCaseImpl implements IRolUseCase {
	private final IRolRepositorio rolRepositorio;

	
	@Override
	public List<Rol> listarActivos() {
        return rolRepositorio.listarActivos();

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Rol> listarTodos() {
		return rolRepositorio.listarTodos();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Rol> buscarPorId(Long id) {
		return rolRepositorio.buscarPorId(id);
	}

	@Override
	@Transactional
	public Rol guardar(Rol Rol) {
		if (rolRepositorio.existePorNombre(Rol.getNombre())) {
			throw new RuntimeException("Ya existe un área con el nombre: " + Rol.getNombre());
		}
		Rol.setEstado(true);
		return rolRepositorio.guardar(Rol);
	}

	@Override
	@Transactional
	public Rol actualizar(Long id, Rol rolEntrada) {
	    Rol rolDb = rolRepositorio.buscarPorId(id)
	            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

	    AppUtil.copiarPropiedadesNoNulas(rolEntrada, rolDb);

	    return rolRepositorio.guardar(rolDb);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		Rol rol = rolRepositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Rol no encontrada con ID: " + id));

		// Eliminación Lógica
		rol.setEstado(false);
		rolRepositorio.guardar(rol);
	}

	@Override
	public boolean existePorNombre(String nombre) {
		return rolRepositorio.existePorNombre(nombre);
	}



}