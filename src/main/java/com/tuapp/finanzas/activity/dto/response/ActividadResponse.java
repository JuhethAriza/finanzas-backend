package com.tuapp.finanzas.activity.dto.response;

import java.time.Instant;

public record ActividadResponse(
        Long id,
        String nombre,
        String descripcion,
        Long proyectoId,
        int totalTasks,
        int completedTasks,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}
