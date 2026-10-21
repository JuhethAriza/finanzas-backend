package com.tuapp.finanzas.project.domain.exception;

public class ProyectoNoEncontradoException extends RuntimeException {
    public ProyectoNoEncontradoException(Long id) {
        super("Proyecto no encontrado con id: " + id);
    }
}