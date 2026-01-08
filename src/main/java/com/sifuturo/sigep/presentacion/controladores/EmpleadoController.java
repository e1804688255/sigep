package com.sifuturo.sigep.presentacion.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifuturo.sigep.aplicacion.casosuso.entrada.IEmpleadoUseCase;
import com.sifuturo.sigep.dominio.entidades.Empleado;
import com.sifuturo.sigep.presentacion.dto.EmpleadoDto;
import com.sifuturo.sigep.presentacion.mapeadores.IEmpleadoDtoMapper;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin(origins = "*") // Permite peticiones desde Angular/React local
public class EmpleadoController {

	private final IEmpleadoUseCase useCase;
	private final IEmpleadoDtoMapper mapper;

	public EmpleadoController(IEmpleadoUseCase useCase, IEmpleadoDtoMapper mapper) {
		this.useCase = useCase;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<EmpleadoDto> crear(@RequestBody EmpleadoDto empleadoDto) {
	    Empleado empleadoDominio = mapper.toDomain(empleadoDto);
	    Empleado empleadoGuardado = useCase.guardar(empleadoDominio);
	    
	    return new ResponseEntity<>(mapper.toDto(empleadoGuardado), HttpStatus.CREATED);
	}
}
