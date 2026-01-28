package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.RecursoNoEncontradoException;
import com.sifuturo.sigep.aplicacion.casosuso.excepciones.ReglaNegocioException;
import com.sifuturo.sigep.dominio.entidades.Area;
import com.sifuturo.sigep.dominio.entidades.Cargo;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.Persona;
import com.sifuturo.sigep.dominio.repositorios.IAreaRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ICargoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.IPersonaRepositorio;

@Service
public class EmpleadoUseCaseImpl implements IEmpleadoUseCase {

    private final IEmpleadoRepositorio empleadoRepositorio;
    private final IPersonaRepositorio personaRepositorio;
    private final IAreaRepositorio areaRepositorio;
    private final ICargoRepositorio cargoRepositorio;

    public EmpleadoUseCaseImpl(IEmpleadoRepositorio empleadoRepositorio, IPersonaRepositorio personaRepositorio,
            IAreaRepositorio areaRepositorio, ICargoRepositorio cargoRepositorio) {
        this.empleadoRepositorio = empleadoRepositorio;
        this.personaRepositorio = personaRepositorio;
        this.areaRepositorio = areaRepositorio;
        this.cargoRepositorio = cargoRepositorio;
    }

    @Override
    @Transactional
    public Empleado guardar(Empleado empleado) {

        // 1. Validar y Cargar Persona
        if (empleado.getPersona() == null || empleado.getPersona().getId() == null) {
            throw new ReglaNegocioException("Debe indicar el ID de la persona.");
        }
        Long idPersona = empleado.getPersona().getId();
        Persona personaCompleta = personaRepositorio.buscarPorId(idPersona)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la persona con ID: " + idPersona));
        empleado.setPersona(personaCompleta);

        // 2. NUEVO: Validar y Cargar Área (Aquí arreglamos los nulos)
        if (empleado.getArea() == null || empleado.getArea().getId() == null) {
            throw new ReglaNegocioException("Debe indicar el ID del Área.");
        }
        Long idArea = empleado.getArea().getId();
        Area areaCompleta = areaRepositorio.buscarPorId(idArea)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el Área con ID: " + idArea));
        empleado.setArea(areaCompleta); // <-- Seteamos el objeto completo con nombre

        // 3. NUEVO: Validar y Cargar Cargo (Aquí arreglamos los nulos)
        if (empleado.getCargo() == null || empleado.getCargo().getId() == null) {
            throw new ReglaNegocioException("Debe indicar el ID del Cargo.");
        }
        Long idCargo = empleado.getCargo().getId();
        Cargo cargoCompleto = cargoRepositorio.buscarPorId(idCargo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el Cargo con ID: " + idCargo));
        empleado.setCargo(cargoCompleto); // <-- Seteamos el objeto completo con nombre

        // 4. Validar duplicidad de código
        Optional<Empleado> existente = empleadoRepositorio.buscarPorCodigo(empleado.getCodigoEmpleado());
        if (existente.isPresent()) {
            if (empleado.getIdEmpleado() == null || !existente.get().getIdEmpleado().equals(empleado.getIdEmpleado())) {
                throw new ReglaNegocioException("El código de empleado " + empleado.getCodigoEmpleado() + " ya existe.");
            }
        }

        return empleadoRepositorio.guardar(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepositorio.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> buscarPorCodigo(String codigo) {
        return empleadoRepositorio.buscarPorCodigo(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listarTodos() {
        return empleadoRepositorio.listarTodos();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        empleadoRepositorio.eliminar(id);
    }
}