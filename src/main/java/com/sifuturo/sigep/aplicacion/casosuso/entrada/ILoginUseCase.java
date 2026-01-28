package com.sifuturo.sigep.aplicacion.casosuso.entrada;

import com.sifuturo.sigep.presentacion.dto.LoginRequestDto;
import com.sifuturo.sigep.presentacion.dto.LoginResponseDto;

public interface ILoginUseCase {
    LoginResponseDto ingresar(LoginRequestDto loginRequest);
}