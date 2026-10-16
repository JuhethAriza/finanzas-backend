package com.tuapp.finanzas.task.dto.request;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import java.time.LocalDate;
import java.util.List;

public record TareaActualizarRequest(String titulo, String descripcion, Long actividadId, Long proyectoId,
									 Long asignadoA, EstadoTarea estado, PrioridadTarea prioridad,
									 LocalDate fechaLimite, List<String> etiquetas) {

}