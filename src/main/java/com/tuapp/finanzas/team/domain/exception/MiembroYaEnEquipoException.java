package com.tuapp.finanzas.team.domain.exception;

public class MiembroYaEnEquipoException extends RuntimeException {
    public MiembroYaEnEquipoException() {
        super("El usuario ya es miembro de este equipo");
    }
}