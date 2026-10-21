package com.tuapp.finanzas.activity.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActividadRequest(
        @NotBlank String nombre,
        String descripcion
) {}
