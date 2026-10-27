package com.tuapp.finanzas.project.domain.model.enums;

/**
 * Estado del ciclo de vida de un proyecto.
 * Los valores se mantienen en español porque es lo que viaja por la API (JSON).
 */
public enum ProjectStatus {
    ACTIVO,
    PAUSADO,
    COMPLETADO,
    CANCELADO
}
