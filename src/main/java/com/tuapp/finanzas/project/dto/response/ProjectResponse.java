package com.tuapp.finanzas.project.dto.response;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String nombre,
        String descripcion,
        Long teamId,
        boolean activo,
        Instant fechaInicio,
        Instant fechaFin,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}