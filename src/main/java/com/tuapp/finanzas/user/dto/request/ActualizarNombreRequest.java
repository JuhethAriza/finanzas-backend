package com.tuapp.finanzas.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarNombreRequest(
        @NotBlank String nombre
) {}
