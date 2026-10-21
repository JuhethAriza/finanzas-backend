package com.tuapp.finanzas.task.dto.request;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import jakarta.validation.constraints.NotNull;

public record EstadoRequest(@NotNull(message = "El estado es obligatorio") EstadoTarea estado) {

}