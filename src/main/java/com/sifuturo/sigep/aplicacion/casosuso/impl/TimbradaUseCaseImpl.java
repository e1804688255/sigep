package com.sifuturo.sigep.aplicacion.casosuso.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.ITimbradaUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.dominio.entidades.enums.TipoTimbrada;
import com.sifuturo.sigep.dominio.repositorios.IEmpleadoRepositorio;
import com.sifuturo.sigep.dominio.repositorios.ITimbradaRepositorio;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimbradaUseCaseImpl implements ITimbradaUseCase {

    private final ITimbradaRepositorio timbradaRepositorio;
    private final IEmpleadoRepositorio empleadoRepositorio;

    @Override
    @Transactional
    public Timbrada registrar(RegistrarTimbradaDto dto) {
        // 1. Validar empleado
        Empleado empleado = empleadoRepositorio.buscarPorId(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // 2. Obtener la última timbrada registrada
        Timbrada ultimaTimbrada = timbradaRepositorio.buscarUltimaPorEmpleado(dto.getIdEmpleado());
        
        // 3. VALIDAR SECUENCIA LÓGICA (Aquí está la magia)
        validarReglasDeNegocio(ultimaTimbrada, dto.getTipo());

        // 4. Guardar
        Timbrada nuevaTimbrada = Timbrada.builder()
                .empleado(empleado)
                .fechaHora(LocalDateTime.now())
                .tipo(dto.getTipo())
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .observacion(dto.getObservacion())
                .build();
        
        nuevaTimbrada.setEstado(true);

        return timbradaRepositorio.guardar(nuevaTimbrada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Timbrada> listarMisTimbradas(Long idEmpleado) {
        return timbradaRepositorio.listarPorEmpleado(idEmpleado);
    }
    
    private void validarReglasDeNegocio(Timbrada ultima, TipoTimbrada nuevoTipo) {
        LocalDate hoy = LocalDate.now();

        // CASO 1: Es la primera vez que timbra EN SU VIDA o NO ha timbrado HOY
        if (ultima == null || !ultima.getFechaHora().toLocalDate().equals(hoy)) {
            if (nuevoTipo != TipoTimbrada.ENTRADA) {
                throw new RuntimeException("El primer registro del día debe ser una ENTRADA.");
            }
            return; 
        }

        // CASO 2: Ya timbró hoy, validamos la secuencia lógica
        TipoTimbrada ultimoTipo = ultima.getTipo();

      
        if (nuevoTipo == TipoTimbrada.ENTRADA) {
             throw new RuntimeException("Ya registraste tu ENTRADA el día de hoy.");
        }

        // Regla: Secuencia de Almuerzo y Salida
        switch (ultimoTipo) {
            case ENTRADA:
                if (nuevoTipo != TipoTimbrada.ALMUERZO_INICIO && nuevoTipo != TipoTimbrada.SALIDA) {
                    throw new RuntimeException("Después de la ENTRADA debes registrar INICIO DE ALMUERZO o SALIDA.");
                }
                break;

            case ALMUERZO_INICIO:
                // Después de ir a comer, SOLO puedes regresar de comer
                if (nuevoTipo != TipoTimbrada.ALMUERZO_FIN) {
                    throw new RuntimeException("Estás en hora de almuerzo. Debes registrar FIN DE ALMUERZO.");
                }
                break;

            case ALMUERZO_FIN:
                // Después de regresar del almuerzo, SOLO puedes SALIR del trabajo
                if (nuevoTipo != TipoTimbrada.SALIDA) {
                    throw new RuntimeException("Ya regresaste del almuerzo. El siguiente registro debe ser SALIDA.");
                }
                break;

            case SALIDA:
                // Si ya marcó SALIDA hoy, ya no debería poder hacer nada más hasta mañana.
                throw new RuntimeException("Ya registraste tu SALIDA de jornada hoy. Hasta mañana.");
        }
    }
}