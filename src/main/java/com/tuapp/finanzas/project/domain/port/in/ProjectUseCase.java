package com.tuapp.finanzas.project.domain.port.in;

import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ProjectUseCase {

    Project crear(String nombre, String descripcion, Long teamId, Long responsableId,
                  ProjectStatus estado, Instant fechaInicio, Instant fechaFin);

    Project obtenerPorId(Long id);

    Page<Project> listar(Long teamId, ProjectStatus estado, Pageable pageable);

    /** Actualización parcial: los argumentos null no modifican el campo. */
    Project actualizar(Long id, String nombre, String descripcion, ProjectStatus estado,
                       Instant fechaInicio, Instant fechaFin, Long responsableId);

    void cambiarEstado(Long id, ProjectStatus nuevoEstado);

    void eliminar(Long id);

    List<MiembroProyecto> listarMiembros(Long proyectoId);

    void agregarMiembro(Long proyectoId, Long usuarioId);

    void eliminarMiembro(Long proyectoId, Long usuarioId);

    EstadisticasProyecto obtenerStats(Long proyectoId);

    record MiembroProyecto(Long usuarioId, Instant fechaIngreso) {}

    record EstadisticasProyecto(Long proyectoId, long totalTareas, long tareasCompletadas,
                                double porcentajeProgreso, Map<String, Long> tareasPorEstado) {}
}
