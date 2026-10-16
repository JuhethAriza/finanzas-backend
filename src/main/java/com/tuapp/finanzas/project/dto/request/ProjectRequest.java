package com.tuapp.finanzas.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ProjectRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        String descripcion,
        @NotNull(message = "El teamId es obligatorio") Long teamId,
        @NotNull(message = "La fecha de inicio es obligatoria") Instant fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria") Instant fechaFin
) {}