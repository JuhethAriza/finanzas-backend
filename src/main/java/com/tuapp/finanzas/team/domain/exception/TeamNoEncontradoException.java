package com.tuapp.finanzas.team.domain.exception;

public class TeamNoEncontradoException extends RuntimeException {
    public TeamNoEncontradoException(Long id) {
        super("Equipo no encontrado: " + id);
    }
}