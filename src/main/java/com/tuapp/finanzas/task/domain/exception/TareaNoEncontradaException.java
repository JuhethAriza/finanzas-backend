package com.tuapp.finanzas.task.domain.exception;

public class TareaNoEncontradaException extends RuntimeException {
    public TareaNoEncontradaException(Long id) {
        super("Tarea no encontrada con id: " + id);
    }
}