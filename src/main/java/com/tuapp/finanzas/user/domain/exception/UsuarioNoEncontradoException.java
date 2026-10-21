package com.tuapp.finanzas.user.domain.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Long id) {
        super("Usuario no encontrado: " + id);
    }
}
