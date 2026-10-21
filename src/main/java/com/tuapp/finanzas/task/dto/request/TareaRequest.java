package com.tuapp.finanzas.task.dto.request;

import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record TareaRequest(
                @NotBlank(message = "El título es obligatorio") String titulo,
                String descripcion,
                @NotNull(message = "El actividadId es obligatorio") Long actividadId,
                Long asignadoA,
                EstadoTarea estado,
                PrioridadTarea prioridad,
                LocalDate fechaLimite,
                List<String> etiquetas,
                @NotNull(message = "La fecha de inicio es obligatoria") Instant fechaInicio,
                @NotNull(message = "La fecha de fin es obligatoria") Instant fechaFin) {
}