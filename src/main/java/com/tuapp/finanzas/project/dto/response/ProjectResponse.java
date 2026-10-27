package com.tuapp.finanzas.project.dto.response;

import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String nombre,
        String descripcion,
        Long teamId,
        Long responsableId,
        ProjectStatus estado,
        boolean activo,
        Instant fechaInicio,
        Instant fechaFin,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}
