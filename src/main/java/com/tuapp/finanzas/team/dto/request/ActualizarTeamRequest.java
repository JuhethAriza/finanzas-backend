package com.tuapp.finanzas.team.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarTeamRequest(
        @NotBlank String nombre,
        String descripcion
) {}