package com.tuapp.finanzas.task.controller;

import com.tuapp.finanzas.activitylog.service.ActivityLogService;
import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.activity.domain.port.in.ActividadUseCase;
import com.tuapp.finanzas.task.domain.port.in.TareaUseCase;
import com.tuapp.finanzas.task.repository.ActividadTareaJpaRepository;
import com.tuapp.finanzas.task.dto.request.*;
import com.tuapp.finanzas.task.dto.response.TareaResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/projects/{projectId}/activities/{activityId}/tasks")
public class TareaController {

    private final TareaUseCase tareaUseCase;
    private final ActividadUseCase actividadUseCase;
    private final ActivityLogService activityLogService;

    public TareaController(TareaUseCase tareaUseCase, ActividadUseCase actividadUseCase,
            ActividadTareaJpaRepository actividadTareaRepository,
            ActivityLogService activityLogService) {
        this.tareaUseCase = tareaUseCase;
        this.actividadUseCase = actividadUseCase;
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ResponseEntity<Page<TareaResponse>> listar(
            @PathVariable Long projectId,
            @PathVariable Long activityId,
            @RequestParam(required = false) Long asignadoA,
            @RequestParam(required = false) EstadoTarea estado,
            @RequestParam(required = false) PrioridadTarea prioridad,
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            Pageable pageable) {
        validarActividadDelProyecto(projectId, activityId);
        Page<TareaResponse> pagina = tareaUseCase.listar(projectId, activityId, asignadoA, estado, prioridad,
                buscar, desde, hasta, pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(
            @PathVariable Long projectId,
            @PathVariable Long activityId,
            @Valid @RequestBody TareaRequest request,
            Authentication authentication) {
        validarActividadDelProyecto(projectId, activityId);
        Tarea tarea = tareaUseCase.crear(request.titulo(), request.descripcion(), activityId, projectId,
                request.asignadoA(), request.estado(), request.prioridad(), request.fechaLimite(), request.etiquetas(),
                request.fechaInicio(), request.fechaFin());
        logGlobal(actor(authentication), "TAREA_CREADA", "Task", tarea.getId(), tarea.getTitulo(),
                projectId, null, "creó la tarea \"" + tarea.getTitulo() + "\"");
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(tarea));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(tareaUseCase.obtenerPorId(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponse> actualizar(@PathVariable Long id,
            @RequestBody TareaActualizarRequest request,
            Authentication authentication) {
        Tarea tarea = tareaUseCase.actualizar(id, request.titulo(), request.descripcion(),
                request.actividadId(), request.proyectoId(), request.asignadoA(), request.estado(), request.prioridad(),
                request.fechaLimite(), request.etiquetas(),
                request.fechaInicio(), request.fechaFin());
        logGlobal(actor(authentication), "TAREA_ACTUALIZADA", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "actualizó la tarea \"" + tarea.getTitulo() + "\"");
        return ResponseEntity.ok(toResponse(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> reemplazar(@PathVariable Long id,
            @Valid @RequestBody TareaActualizarRequest request,
            Authentication authentication) {
        Tarea tarea = tareaUseCase.reemplazar(id, request.titulo(), request.descripcion(),
                request.actividadId(), request.proyectoId(), request.asignadoA(), request.estado(), request.prioridad(),
                request.fechaLimite(), request.etiquetas(),
                request.fechaInicio(), request.fechaFin());
        logGlobal(actor(authentication), "TAREA_REEMPLAZADA", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "reemplazó la tarea \"" + tarea.getTitulo() + "\"");
        return ResponseEntity.ok(toResponse(tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication authentication) {
        Tarea tarea = tareaUseCase.obtenerPorId(id);
        tareaUseCase.eliminar(id);
        logGlobal(actor(authentication), "TAREA_ELIMINADA", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "eliminó la tarea \"" + tarea.getTitulo() + "\"");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TareaResponse> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) EstadoRequest bodyRequest,
            @RequestParam(required = false) String estado,
            Authentication authentication) {
        EstadoTarea nuevoEstado = null;
        if (bodyRequest != null) {
            nuevoEstado = bodyRequest.estado();
        } else if (estado != null && !estado.isBlank()) {
            nuevoEstado = EstadoTarea.fromValue(estado);
        }
        if (nuevoEstado == null) {
            throw new IllegalArgumentException(
                    "El estado es obligatorio. Envialo en el body como {\"estado\": \"EN_PROGRESO\"} o como query param ?estado=PENDIENTE");
        }
        Tarea tarea = tareaUseCase.cambiarEstado(id, nuevoEstado);
        logGlobal(actor(authentication), "ESTADO_CAMBIADO", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "cambió estado a " + tarea.getEstado().name());
        return ResponseEntity.ok(toResponse(tarea));
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<TareaResponse> asignar(@PathVariable Long id,
            @Valid @RequestBody AsignarRequest request,
            Authentication authentication) {
        Tarea tarea = tareaUseCase.asignar(id, request.usuarioId());
        logGlobal(actor(authentication), "RESPONSABLE_CAMBIADO", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "asignó responsable a la tarea");
        return ResponseEntity.ok(toResponse(tarea));
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TareaResponse> cambiarPrioridad(@PathVariable Long id,
            @Valid @RequestBody PrioridadRequest request,
            Authentication authentication) {
        Tarea tarea = tareaUseCase.cambiarPrioridad(id, request.prioridad());
        logGlobal(actor(authentication), "PRIORIDAD_CAMBIADA", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "cambió prioridad a " + tarea.getPrioridad().name());
        return ResponseEntity.ok(toResponse(tarea));
    }

    @PatchMapping("/{id}/due-date")
    public ResponseEntity<TareaResponse> cambiarFechaLimite(@PathVariable Long id,
            @Valid @RequestBody FechaLimiteRequest request,
            Authentication authentication) {
        Tarea tarea = tareaUseCase.cambiarFechaLimite(id, request.fechaLimite());
        logGlobal(actor(authentication), "FECHA_LIMITE_CAMBIADA", "Task", id, tarea.getTitulo(),
                tarea.getProyectoId(), null, "cambió fecha límite a " + tarea.getFechaLimite());
        return ResponseEntity.ok(toResponse(tarea));
    }

    private TareaResponse toResponse(Tarea t) {
        return new TareaResponse(t.getId(), t.getTitulo(), t.getDescripcion(), t.getProyectoId(), t.getActividadId(),
                t.getAsignadoA(), t.getEstado(), t.getPrioridad(), t.getFechaLimite(), t.getFechaInicio(), t.getFechaFin(), t.getEtiquetas(),
                t.getFechaCreacion(), t.getFechaActualizacion());
    }

    private void validarActividadDelProyecto(Long projectId, Long activityId) {
        var actividad = actividadUseCase.obtenerPorId(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
        if (!projectId.equals(actividad.getProyectoId())) {
            throw new IllegalArgumentException("La actividad no pertenece al proyecto indicado");
        }
    }

    private Long actor(Authentication authentication) {
        return Long.valueOf(authentication.getPrincipal().toString());
    }

    private void logGlobal(Long userId, String action, String resourceType, Long resourceId,
            String resourceName, Long projectId, String projectName, String detail) {
        activityLogService.registrar(userId, action, resourceType, resourceId, resourceName,
                projectId, projectName, detail);
    }
}
