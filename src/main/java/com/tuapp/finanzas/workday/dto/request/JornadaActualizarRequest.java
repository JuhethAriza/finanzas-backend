package com.tuapp.finanzas.workday.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record JornadaActualizarRequest(
        @NotNull LocalDate fecha,
        @NotNull Double horasTrabajadas,
        String notas
) {}
