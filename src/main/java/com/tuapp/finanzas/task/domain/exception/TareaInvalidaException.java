package com.tuapp.finanzas.task.domain.exception;

public class TareaInvalidaException extends RuntimeException {
    public TareaInvalidaException(String mensaje) {
        super(mensaje);
    }
}