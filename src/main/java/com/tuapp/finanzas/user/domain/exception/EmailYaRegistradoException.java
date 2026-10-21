package com.tuapp.finanzas.user.domain.exception;

public class EmailYaRegistradoException extends RuntimeException {
    public EmailYaRegistradoException() {
        super("El email ya está registrado.");
    }
}
