package com.tuapp.finanzas.project.dto.response;

import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;
import java.time.Instant;
import java.time.LocalDate;

public record ProyectoResponse(
        Long id,
        String nombre,
        String descripcion,
        ProyectoEstado estado,
        Long responsableId,
        Long equipoId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}
