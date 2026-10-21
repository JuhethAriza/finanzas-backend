package com.tuapp.finanzas.project.service;

import com.tuapp.finanzas.project.domain.exception.ProyectoInvalidoException;
import com.tuapp.finanzas.project.domain.exception.ProyectoNoEncontradoException;
import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;
import com.tuapp.finanzas.project.domain.port.in.ProjectUseCase;
import com.tuapp.finanzas.project.domain.port.out.ProjectMemberRepositoryPort;
import com.tuapp.finanzas.project.domain.port.out.ProjectRepositoryPort;
import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.port.out.TareaRepositoryPort;
import com.tuapp.finanzas.team.domain.port.in.TeamMemberUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService implements ProjectUseCase {

    private final ProjectRepositoryPort proyectoRepositoryPort;
    private final ProjectMemberRepositoryPort miembroRepositoryPort;
    private final TareaRepositoryPort tareaRepositoryPort;
    private final TeamMemberUseCase teamMemberUseCase;

    public ProjectService(ProjectRepositoryPort proyectoRepositoryPort,
                          ProjectMemberRepositoryPort miembroRepositoryPort,
                          TareaRepositoryPort tareaRepositoryPort,
                          TeamMemberUseCase teamMemberUseCase) {
        this.proyectoRepositoryPort = proyectoRepositoryPort;
        this.miembroRepositoryPort = miembroRepositoryPort;
        this.tareaRepositoryPort = tareaRepositoryPort;
        this.teamMemberUseCase = teamMemberUseCase;
    }

    @Override
    @Transactional
    public Project crear(String nombre, String descripcion, Long teamId, Long responsableId,
                         ProjectStatus estado, Instant fechaInicio, Instant fechaFin) {
        Project proyecto = Project.crear(nombre, descripcion, teamId, responsableId, estado,
                fechaInicio, fechaFin);
        return proyectoRepositoryPort.save(proyecto);
    }

    @Override
    public Project obtenerPorId(Long id) {
        return proyectoRepositoryPort.findById(id)
                .orElseThrow(() -> new ProyectoNoEncontradoException(id));
    }

    @Override
    public Page<Project> listar(Long teamId, ProjectStatus estado, Pageable pageable) {
        return proyectoRepositoryPort.findByFiltros(teamId, estado, pageable);
    }

    @Override
    @Transactional
    public Project actualizar(Long id, String nombre, String descripcion, ProjectStatus estado,
                              Instant fechaInicio, Instant fechaFin, Long responsableId) {
        Project proyecto = obtenerPorId(id);
        proyecto.actualizar(nombre, descripcion, estado, fechaInicio, fechaFin, responsableId);
        return proyectoRepositoryPort.save(proyecto);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, ProjectStatus nuevoEstado) {
        Project proyecto = obtenerPorId(id);
        proyecto.cambiarEstado(nuevoEstado);
        proyectoRepositoryPort.save(proyecto);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!proyectoRepositoryPort.existsById(id)) {
            throw new ProyectoNoEncontradoException(id);
        }
        proyectoRepositoryPort.deleteById(id);
    }

    @Override
    public List<MiembroProyecto> listarMiembros(Long proyectoId) {
        obtenerPorId(proyectoId);
        return miembroRepositoryPort.listarPorProyecto(proyectoId);
    }

    @Override
    @Transactional
    public void agregarMiembro(Long proyectoId, Long usuarioId) {
        Project proyecto = obtenerPorId(proyectoId);

        // Regla heredada del módulo Proyecto: solo miembros del equipo del proyecto pueden unirse.
        boolean esMiembroDelEquipo = teamMemberUseCase.listarMiembros(proyecto.getTeamId()).stream()
                .anyMatch(m -> m.getUserId().equals(usuarioId));
        if (!esMiembroDelEquipo) {
            throw new ProyectoInvalidoException(
                    "El usuario no es miembro del equipo al que pertenece este proyecto");
        }

        if (miembroRepositoryPort.buscar(proyectoId, usuarioId).isPresent()) {
            return; // idempotente: ya era miembro
        }
        miembroRepositoryPort.agregar(proyectoId, usuarioId);
    }

    @Override
    @Transactional
    public void eliminarMiembro(Long proyectoId, Long usuarioId) {
        obtenerPorId(proyectoId);
        miembroRepositoryPort.eliminar(proyectoId, usuarioId);
    }

    @Override
    public EstadisticasProyecto obtenerStats(Long proyectoId) {
        obtenerPorId(proyectoId);
        long total = tareaRepositoryPort.countByProyectoId(proyectoId);
        long completadas = tareaRepositoryPort.countByProyectoIdAndEstado(proyectoId, EstadoTarea.COMPLETADA);
        double porcentaje = total == 0 ? 0.0 : (completadas * 100.0) / total;

        Map<String, Long> porEstado = new LinkedHashMap<>();
        for (EstadoTarea estado : EstadoTarea.values()) {
            porEstado.put(estado.name(), tareaRepositoryPort.countByProyectoIdAndEstado(proyectoId, estado));
        }

        return new EstadisticasProyecto(proyectoId, total, completadas,
                Math.round(porcentaje * 100.0) / 100.0, porEstado);
    }
}
