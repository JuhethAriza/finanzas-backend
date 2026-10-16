package com.tuapp.finanzas.activity.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActividadActualizarRequest(
        @NotBlank String nombre,
        String descripcion
) {}
