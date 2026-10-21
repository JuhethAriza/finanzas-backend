package com.tuapp.finanzas.activity.service;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import com.tuapp.finanzas.activity.domain.port.in.ActividadUseCase;
import com.tuapp.finanzas.activity.domain.port.out.ActividadRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ActividadService implements ActividadUseCase {
    private final ActividadRepositoryPort repositoryPort;

    public ActividadService(ActividadRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Page<Actividad> listar(Long proyectoId, Pageable pageable) {
        return repositoryPort.listarPorProyectoId(proyectoId, pageable);
    }

    @Override
    public Page<Actividad> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return repositoryPort.listarPorUsuario(usuarioId, pageable);
    }

    @Override
    @Transactional
    public Actividad crear(String nombre, String descripcion, Long proyectoId) {
        Actividad actividad = Actividad.crear(nombre, descripcion, proyectoId);
        return repositoryPort.guardar(actividad);
    }

    @Override
    public Optional<Actividad> obtenerPorId(Long id) {
        return repositoryPort.buscarPorId(id);
    }

    @Override
    @Transactional
    public Actividad actualizar(Long id, String nombre, String descripcion) {
        Actividad actividad = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + id));
        actividad.actualizar(nombre, descripcion);
        return repositoryPort.guardar(actividad);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repositoryPort.eliminar(id);
    }

    @Override
    @Transactional
    public void incrementTotalTasks(Long actividadId) {
        Actividad actividad = repositoryPort.buscarPorId(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));
        actividad.incrementTotalTasks();
        repositoryPort.guardar(actividad);
    }

    @Override
    @Transactional
    public void decrementTotalTasks(Long actividadId) {
        Actividad actividad = repositoryPort.buscarPorId(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));
        actividad.decrementTotalTasks();
        repositoryPort.guardar(actividad);
    }

    @Override
    @Transactional
    public void incrementCompletedTasks(Long actividadId) {
        Actividad actividad = repositoryPort.buscarPorId(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));
        actividad.incrementCompletedTasks();
        repositoryPort.guardar(actividad);
    }

    @Override
    @Transactional
    public void decrementCompletedTasks(Long actividadId) {
        Actividad actividad = repositoryPort.buscarPorId(actividadId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con ID: " + actividadId));
        actividad.decrementCompletedTasks();
        repositoryPort.guardar(actividad);
    }
}
