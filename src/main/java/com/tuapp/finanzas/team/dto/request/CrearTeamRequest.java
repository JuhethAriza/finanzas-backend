package com.tuapp.finanzas.team.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearTeamRequest(
        @NotBlank String nombre,
        String descripcion
) {}