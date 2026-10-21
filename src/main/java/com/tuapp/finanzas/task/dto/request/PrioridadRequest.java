package com.tuapp.finanzas.task.dto.request;

import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import jakarta.validation.constraints.NotNull;

public record PrioridadRequest(@NotNull(message = "La prioridad es obligatoria") PrioridadTarea prioridad) {

}