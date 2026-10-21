package com.tuapp.finanzas.task.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record FechaLimiteRequest(@NotNull LocalDate fechaLimite) {}
