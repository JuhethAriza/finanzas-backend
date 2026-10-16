package com.tuapp.finanzas.project.domain.exception;

public class ProyectoInvalidoException extends RuntimeException {
    public ProyectoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
