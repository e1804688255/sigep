package com.sifuturo.sigep.presentacion.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sifuturo.sigep.aplicacion.casosuso.entrada.ILoginUseCase;
import com.sifuturo.sigep.presentacion.dto.LoginRequestDto;
import com.sifuturo.sigep.presentacion.dto.LoginResponseDto;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final ILoginUseCase loginUseCase;

    public AuthController(ILoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = loginUseCase.ingresar(request);
        return ResponseEntity.ok(response);
    }
}