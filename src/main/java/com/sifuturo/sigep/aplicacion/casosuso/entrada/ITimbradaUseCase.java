package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import java.util.List;
import com.sifuturo.sigep.dominio.entidades.Timbrada;
import com.sifuturo.sigep.presentacion.dto.RegistrarTimbradaDto;

public interface ITimbradaUseCase {
    Timbrada registrar(RegistrarTimbradaDto dto);
    List<Timbrada> listarMisTimbradas(Long idEmpleado);
}