package com.tuapp.finanzas.project.dto.request;

import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;

import java.time.Instant;

/** PATCH parcial: los campos que lleguen null no se modifican. */
public record ProjectActualizarRequest(
        String nombre,
        String descripcion,
        ProjectStatus estado,
        Instant fechaInicio,
        Instant fechaFin,
        Long responsableId
) {}
