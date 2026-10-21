package com.tuapp.finanzas.task.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoTarea {
    BACKLOG, 
    POR_HACER, 
    EN_PROGRESO, 
    EN_REVISION, 
    COMPLETADA,
    PENDIENTE;

    @JsonCreator
    public static EstadoTarea fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        
        try {
            return EstadoTarea.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            String normalized = value.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            try {
                return EstadoTarea.valueOf(normalized);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Valor de estado de tarea no válido: " + value);
            }
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}