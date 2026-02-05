package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import com.sifuturo.sigep.presentacion.dto.FichaMedicaDTO;

public interface IGestionarFichaMedicaUseCase {
    FichaMedicaDTO guardarFicha(FichaMedicaDTO dto);
}