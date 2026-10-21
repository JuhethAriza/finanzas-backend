package com.tuapp.finanzas.activity.domain.port.in;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActividadUseCase {
    Page<Actividad> listar(Long proyectoId, Pageable pageable);
    Page<Actividad> listarPorUsuario(Long usuarioId, Pageable pageable);
    Actividad crear(String nombre, String descripcion, Long proyectoId);
    Optional<Actividad> obtenerPorId(Long id);
    Actividad actualizar(Long id, String nombre, String descripcion);
    void eliminar(Long id);

    // Counter methods
    void incrementTotalTasks(Long actividadId);
    void decrementTotalTasks(Long actividadId);
    void incrementCompletedTasks(Long actividadId);
    void decrementCompletedTasks(Long actividadId);
}
