package com.tuapp.finanzas.task.dto.request;

import jakarta.validation.constraints.NotNull;

public record AsignarRequest(@NotNull(message = "El usuarioId es obligatorio") Long usuarioId) {

}