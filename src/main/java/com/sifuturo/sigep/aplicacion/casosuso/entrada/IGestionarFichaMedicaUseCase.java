package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import com.sifuturo.sigep.presentacion.dto.RegistrarFichaDto;

public interface IGestionarFichaMedicaUseCase {
	RegistrarFichaDto guardarFicha(RegistrarFichaDto dto);
}