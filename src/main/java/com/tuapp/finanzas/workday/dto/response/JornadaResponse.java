package com.tuapp.finanzas.workday.dto.response;

import java.time.Instant;
import java.time.LocalDate;

public record JornadaResponse(
        Long id,
        Long tareaId,
        LocalDate fecha,
        Double horasTrabajadas,
        String notas,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {}
