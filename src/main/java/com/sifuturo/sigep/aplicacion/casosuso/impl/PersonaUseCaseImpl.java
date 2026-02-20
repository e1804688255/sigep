package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IPersonaUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException; // <--- Importar
import com.sifuturo.sigep.aplicacion.util.AppUtil;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.entidades.enums.EstadoPersona;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PersonaUseCaseImpl implements IPersonaUseCase {

	private final IPersonaRepositorio repositorio;
	private final ICargoRepositorio cargoRepositorio;

	@Override
	@Transactional
	public Persona crear(Persona persona) {
		if (repositorio.obtenerPorCedula(persona.getCedula()).isPresent()) {
			throw new ReglaNegocioException("La persona con cédula " + persona.getCedula() + " ya está registrada.");
		}
        if (persona.getEstadoPersona() == null) {
            persona.setEstadoPersona(EstadoPersona.CANDIDATO);
        }
        if (persona.getFechaPostulacion()== null) {
            persona.setFechaPostulacion(LocalDate.now());
        }
		persona.setEstado(true);

		return repositorio.crear(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> obtenerPorCedula(String cedula) {
		// Opción A: Retornar Optional (El controlador decide qué hacer)
		return repositorio.obtenerPorCedula(cedula);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listarTodos() {
		return repositorio.listarTodos();

	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listarActivos() {
		return repositorio.listarActivos();

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> buscarPorId(Long id) {
        return repositorio.buscarPorId(id);

	}

	@Override
	public boolean existsByCedula(String cedula) {
        return repositorio.existsByCedula(cedula);

	}
	@Override
	@Transactional
	public Persona actualizar(Long id, Persona personaModificada) {
		// 1. Buscamos la persona original
		Persona personaDb = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada"));

		// Respaldamos el estado original para no perderlo
		Boolean estadoOriginal = personaDb.getEstado();

		// -------------------------------------------------------
		// CORRECCIÓN DEL ERROR DE TIPOS (Cargo vs CargoEntity)
		// -------------------------------------------------------
		if (personaModificada.getCargoPostulacion() != null && personaModificada.getCargoPostulacion().getId() != null) {
			Long idCargo = personaModificada.getCargoPostulacion().getId();
			
			// a) Validamos que el cargo exista en la base de datos
			// (Esto devuelve un objeto 'Cargo' de dominio)
			var cargoDomain = cargoRepositorio.buscarPorId(idCargo)
					.orElseThrow(() -> new RecursoNoEncontradoException("El cargo no existe"));
			
			// b) CONVERSIÓN MANUAL: Transformamos 'Cargo' -> 'CargoEntity'
			// Esto es necesario porque tu clase Persona espera una Entity
			com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity cargoEntity = new com.sifuturo.sigep.infraestructura.persistencia.jpa.CargoEntity();
			cargoEntity.setId(cargoDomain.getId());
			cargoEntity.setNombre(cargoDomain.getNombre());
			// Copia otros campos si son necesarios
			
			// c) Asignamos la Entity convertida
			personaModificada.setCargoPostulacion(cargoEntity);
			
		} else {
			// Si no enviaron cargo, mantenemos el que ya tenía la persona
			personaModificada.setCargoPostulacion(personaDb.getCargoPostulacion());
		}

		// 2. Copiamos propiedades
		personaModificada.setId(id);
		AppUtil.copiarPropiedadesNoNulas(personaModificada, personaDb);

		// 3. Restauramos estado si se perdió
		if (personaDb.getEstado() == null) {
			personaDb.setEstado(estadoOriginal);
		}

		// 4. Lógica de Rechazo/Reactivación
		if (personaModificada.getEstadoPersona() != null) {
			personaDb.setEstadoPersona(personaModificada.getEstadoPersona());
			// Al cambiar el estado de postulación, aseguramos que el registro siga activo
			personaDb.setEstado(true);
		}

		return repositorio.crear(personaDb);
	}
	
	@Override
	@Transactional
	public void eliminar(Long id) {
		Persona persona = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

		persona.setEstado(false);
		repositorio.crear(persona);
	}
}