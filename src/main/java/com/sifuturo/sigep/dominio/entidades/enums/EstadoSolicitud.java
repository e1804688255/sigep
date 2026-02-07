package com.sifuturo.sigep.dominio.entidades.enums;

public enum EstadoSolicitud {
	PENDIENTE_APROBACION_JEFE, // El empleado la creó
    PENDIENTE_AUTORIZACION_TH, // El jefe ya dijo sí, falta TH
    APROBADO,                  // TH autorizó (Fin del ciclo positivo)
    RECHAZADO_JEFE,            // Se cae aquí
    RECHAZADO_TH               // O se cae acá
}
