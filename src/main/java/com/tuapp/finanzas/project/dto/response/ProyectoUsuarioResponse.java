package com.tuapp.finanzas.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProyectoUsuarioResponse(
    Long id,
    Long proyectoId,
    Long usuarioId,
    String nombre,
    String email,
    Instant fechaAsignacion
) {}
