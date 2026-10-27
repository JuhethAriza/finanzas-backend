package com.tuapp.finanzas.project.dto.request;

import jakarta.validation.constraints.NotNull;

public record MiembroRequest(@NotNull(message = "El usuarioId es obligatorio") Long usuarioId) {}