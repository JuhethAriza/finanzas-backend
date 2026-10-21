package com.tuapp.finanzas.user.domain.exception;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}
