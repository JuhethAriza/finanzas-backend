package com.tuapp.finanzas.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;
import java.time.LocalDate;

public record ProyectoRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotNull Long equipoId,
        @NotNull Long responsableId,
        ProyectoEstado estado,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {}
