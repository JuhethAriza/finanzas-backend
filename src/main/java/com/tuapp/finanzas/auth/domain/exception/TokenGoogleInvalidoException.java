package com.tuapp.finanzas.auth.domain.exception;

public class TokenGoogleInvalidoException extends RuntimeException {
    public TokenGoogleInvalidoException() {
        super("Token de Google inválido.");
    }

    public TokenGoogleInvalidoException(String message) {
        super(message);
    }
}
