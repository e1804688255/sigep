package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import com.sifuturo.sigep.dominio.entidades.FichaMedica;
import com.sifuturo.sigep.presentacion.dto.RegistrarFichaDto;

public interface IFichaMedicaUseCase {
    FichaMedica registrarConsulta(RegistrarFichaDto dto);
    List<FichaMedica> obtenerHistorialMedico(Long idEmpleado);
}