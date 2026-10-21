package com.tuapp.finanzas.team.dto.request;

import jakarta.validation.constraints.NotNull;

public record AgregarMiembroRequest(
        @NotNull Long userId,
        String rol
) {}