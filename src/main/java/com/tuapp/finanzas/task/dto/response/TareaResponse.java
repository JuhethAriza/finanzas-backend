package com.tuapp.finanzas.task.dto.response;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record TareaResponse(
        Long id,
        String titulo,
        String descripcion,
        Long proyectoId,
        Long actividadId,
        Long asignadoA,
        EstadoTarea estado,
        PrioridadTarea prioridad,
        LocalDate fechaLimite,
        Instant fechaInicio,
        Instant fechaFin,
        List<String> etiquetas,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}