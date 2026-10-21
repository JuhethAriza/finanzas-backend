package com.tuapp.finanzas.auth.domain.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() {
        super("Credenciales inválidas.");
    }

    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
