package com.tuapp.finanzas.task.service;

import com.tuapp.finanzas.activity.domain.port.in.ActividadUseCase;
import com.tuapp.finanzas.task.domain.exception.TareaNoEncontradaException;
import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.task.domain.port.in.TareaUseCase;
import com.tuapp.finanzas.task.domain.port.out.TareaRepositoryPort;
import com.tuapp.finanzas.task.repository.ChecklistItemJpaRepository;
import com.tuapp.finanzas.task.repository.ComentarioTareaJpaRepository;
import com.tuapp.finanzas.task.repository.ArchivoTareaJpaRepository;
import com.tuapp.finanzas.task.repository.ActividadTareaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class TareaService implements TareaUseCase {

    private final TareaRepositoryPort tareaRepositoryPort;
    private final ActividadUseCase actividadUseCase;
    private final ChecklistItemJpaRepository checklistRepository;
    private final ComentarioTareaJpaRepository comentarioRepository;
    private final ArchivoTareaJpaRepository archivoRepository;
    private final ActividadTareaJpaRepository actividadRepository;

    public TareaService(TareaRepositoryPort tareaRepositoryPort, ActividadUseCase actividadUseCase,
            ChecklistItemJpaRepository checklistRepository,
            ComentarioTareaJpaRepository comentarioRepository,
            ArchivoTareaJpaRepository archivoRepository,
            ActividadTareaJpaRepository actividadRepository) {
        this.tareaRepositoryPort = tareaRepositoryPort;
        this.actividadUseCase = actividadUseCase;
        this.checklistRepository = checklistRepository;
        this.comentarioRepository = comentarioRepository;
        this.archivoRepository = archivoRepository;
        this.actividadRepository = actividadRepository;
    }

    @Override
    @Transactional
    public Tarea crear(String titulo, String descripcion, Long actividadId, Long proyectoId,
            Long asignadoA, EstadoTarea estado, PrioridadTarea prioridad,
            LocalDate fechaLimite, List<String> etiquetas,
            Instant fechaInicio, Instant fechaFin) {
        Tarea tarea = Tarea.crear(titulo, descripcion, actividadId, proyectoId, asignadoA, estado,
                prioridad, fechaLimite, etiquetas, fechaInicio, fechaFin);
        Tarea guardada = tareaRepositoryPort.save(tarea);

        actividadUseCase.incrementTotalTasks(actividadId);
        if (guardada.getEstado() == EstadoTarea.COMPLETADA) {
            actividadUseCase.incrementCompletedTasks(actividadId);
        }

        return guardada;
    }

    @Override
    public Tarea obtenerPorId(Long id) {
        return tareaRepositoryPort.findById(id)
                .orElseThrow(() -> new TareaNoEncontradaException(id));
    }

    @Override
    public Page<Tarea> listar(Long proyectoId, Long actividadId, Long asignadoA, EstadoTarea estado,
            PrioridadTarea prioridad, String buscar, LocalDate desde, LocalDate hasta,
            Pageable pageable) {
        return tareaRepositoryPort.findByFiltros(proyectoId, actividadId, asignadoA, estado, prioridad,
                buscar, desde, hasta, pageable);
    }

    @Override
    @Transactional
    public Tarea actualizar(Long id, String nuevoTitulo, String nuevaDescripcion, Long actividadId, Long proyectoId,
            Long asignadoA,
            EstadoTarea estado, PrioridadTarea prioridad, LocalDate fechaLimite, List<String> etiquetas,
            Instant fechaInicio, Instant fechaFin) {
        Tarea tarea = obtenerPorId(id);
        tarea.actualizarDetalles(nuevoTitulo, nuevaDescripcion, fechaLimite, etiquetas);
        tarea.reprogramarSiCorresponde(fechaInicio, fechaFin);
        if (actividadId != null)
            tarea.setActividadId(actividadId);
        if (proyectoId != null)
            tarea.setProyectoId(proyectoId);
        if (asignadoA != null)
            tarea.asignar(asignadoA);
        if (estado != null && estado != tarea.getEstado())
            tarea.cambiarEstado(estado);
        if (prioridad != null && prioridad != tarea.getPrioridad())
            tarea.cambiarPrioridad(prioridad);
        return tareaRepositoryPort.save(tarea);
    }

    @Override
    @Transactional
    public Tarea reemplazar(Long id, String titulo, String descripcion, Long actividadId, Long proyectoId,
            Long asignadoA,
            EstadoTarea estado, PrioridadTarea prioridad, LocalDate fechaLimite, List<String> etiquetas,
            Instant fechaInicio, Instant fechaFin) {
        Tarea tarea = obtenerPorId(id);
        tarea.reemplazar(titulo, descripcion, actividadId, proyectoId, asignadoA, estado, prioridad, fechaLimite,
                etiquetas);
        tarea.reprogramarSiCorresponde(fechaInicio, fechaFin);
        return tareaRepositoryPort.save(tarea);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tarea tarea = obtenerPorId(id);
        Long actividadId = tarea.getActividadId();
        EstadoTarea estado = tarea.getEstado();

        checklistRepository.deleteByTareaId(id);
        comentarioRepository.deleteByTareaId(id);
        archivoRepository.deleteByTareaId(id);
        actividadRepository.deleteByTareaId(id);
        tareaRepositoryPort.deleteById(id);

        actividadUseCase.decrementTotalTasks(actividadId);
        if (estado == EstadoTarea.COMPLETADA) {
            actividadUseCase.decrementCompletedTasks(actividadId);
        }
    }

    @Override
    @Transactional
    public Tarea cambiarEstado(Long id, EstadoTarea nuevoEstado) {
        Tarea tarea = obtenerPorId(id);
        EstadoTarea estadoAnterior = tarea.getEstado();
        Long actividadId = tarea.getActividadId();

        if (estadoAnterior == nuevoEstado) {
            return tarea;
        }

        tarea.cambiarEstado(nuevoEstado);
        Tarea guardada = tareaRepositoryPort.save(tarea);

        if (nuevoEstado == EstadoTarea.COMPLETADA) {
            actividadUseCase.incrementCompletedTasks(actividadId);
        } else if (estadoAnterior == EstadoTarea.COMPLETADA) {
            actividadUseCase.decrementCompletedTasks(actividadId);
        }

        return guardada;
    }

    @Override
    @Transactional
    public Tarea asignar(Long id, Long usuarioId) {
        Tarea tarea = obtenerPorId(id);
        tarea.asignar(usuarioId);
        return tareaRepositoryPort.save(tarea);
    }

    @Override
    @Transactional
    public Tarea cambiarPrioridad(Long id, PrioridadTarea nuevaPrioridad) {
        Tarea tarea = obtenerPorId(id);
        tarea.cambiarPrioridad(nuevaPrioridad);
        return tareaRepositoryPort.save(tarea);
    }

    @Override
    @Transactional
    public Tarea cambiarFechaLimite(Long id, LocalDate fechaLimite) {
        Tarea tarea = obtenerPorId(id);
        tarea.cambiarFechaLimite(fechaLimite);
        return tareaRepositoryPort.save(tarea);
    }
}
