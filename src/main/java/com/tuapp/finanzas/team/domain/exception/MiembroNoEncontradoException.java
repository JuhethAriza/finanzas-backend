package com.tuapp.finanzas.team.domain.exception;

public class MiembroNoEncontradoException extends RuntimeException {
    public MiembroNoEncontradoException() {
        super("El usuario no es miembro de este equipo");
    }
}