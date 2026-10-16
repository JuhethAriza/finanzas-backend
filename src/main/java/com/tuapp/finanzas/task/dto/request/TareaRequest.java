package com.tuapp.finanzas.task.dto.request;

import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import java.time.Instant;

public record TareaRequest(
        @NotBlank(message = "El título es obligatorio") String titulo,
        String descripcion,
        @NotNull(message = "El actividadId es obligatorio") Long actividadId,
        Long asignadoA,
        EstadoTarea estado,
        PrioridadTarea prioridad,
        LocalDate fechaLimite,
        List<String> etiquetas
) {}