package com.tuapp.finanzas.task.domain.port.in;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

import java.time.Instant;


public interface TareaUseCase {

    Tarea crear(String titulo, String descripcion, Long actividadId, Long proyectoId, Long asignadoA,
                EstadoTarea estado, PrioridadTarea prioridad, LocalDate fechaLimite, List<String> etiquetas,
                Instant fechaInicio, Instant fechaFin);

    Tarea obtenerPorId(Long id);

    Page<Tarea> listar(Long proyectoId, Long actividadId, Long asignadoA, EstadoTarea estado,
                       PrioridadTarea prioridad, String buscar, LocalDate desde, LocalDate hasta, Pageable pageable);

    Tarea actualizar(Long id, String nuevoTitulo, String nuevaDescripcion, Long actividadId, Long proyectoId, Long asignadoA,
                     EstadoTarea estado, PrioridadTarea prioridad, LocalDate fechaLimite, List<String> etiquetas,
                Instant fechaInicio, Instant fechaFin);

    Tarea reemplazar(Long id, String titulo, String descripcion, Long actividadId, Long proyectoId, Long asignadoA,
                     EstadoTarea estado, PrioridadTarea prioridad, LocalDate fechaLimite, List<String> etiquetas,
                Instant fechaInicio, Instant fechaFin);

    void eliminar(Long id);

    Tarea cambiarEstado(Long id, EstadoTarea nuevoEstado);

    Tarea asignar(Long id, Long usuarioId);

    Tarea cambiarPrioridad(Long id, PrioridadTarea nuevaPrioridad);

    Tarea cambiarFechaLimite(Long id, LocalDate fechaLimite);
}