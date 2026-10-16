package com.tuapp.finanzas.project.domain.port.in;

import com.tuapp.finanzas.project.domain.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ProjectUseCase {

    Project crear(String nombre, String descripcion, Long teamId, Instant fechaInicio, Instant fechaFin);

    Project obtenerPorId(Long id);

    Page<Project> listar(Long teamId, Boolean activo, Pageable pageable);

    Project actualizar(Long id, String nuevoNombre, String nuevaDescripcion);

    void eliminar(Long id);

    List<MiembroProyecto> listarMiembros(Long proyectoId);

    void agregarMiembro(Long proyectoId, Long usuarioId);

    void eliminarMiembro(Long proyectoId, Long usuarioId);

    EstadisticasProyecto obtenerStats(Long proyectoId);

    record MiembroProyecto(Long usuarioId, Instant fechaIngreso) {}

    record EstadisticasProyecto(Long proyectoId, long totalTareas, long tareasCompletadas,
                                double porcentajeProgreso, Map<String, Long> tareasPorEstado) {}
}