package com.tuapp.finanzas.task.controller;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.task.domain.port.in.TareaUseCase;
import com.tuapp.finanzas.task.dto.response.TareaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
public class TareaConsultaController {
    private final TareaUseCase tareaUseCase;

    public TareaConsultaController(TareaUseCase tareaUseCase) {
        this.tareaUseCase = tareaUseCase;
    }

    @GetMapping
    public Page<TareaResponse> listar(@RequestParam(required = false) Long proyectoId,
                                      @RequestParam(required = false) Long actividadId,
                                      @RequestParam(required = false) Long asignadoA,
                                      @RequestParam(required = false) EstadoTarea estado,
                                      @RequestParam(required = false) PrioridadTarea prioridad,
                                      @RequestParam(required = false) String buscar,
                                      @RequestParam(required = false) LocalDate desde,
                                      @RequestParam(required = false) LocalDate hasta,
                                      Pageable pageable) {
        return tareaUseCase.listar(proyectoId, actividadId, asignadoA, estado, prioridad, buscar, desde, hasta, pageable)
                .map(TareaConsultaController::toResponse);
    }

    @GetMapping("/mine")
    public Page<TareaResponse> misTareas(@RequestParam(required = false) Long proyectoId,
                                         @RequestParam(required = false) EstadoTarea estado,
                                         @RequestParam(required = false) PrioridadTarea prioridad,
                                         @RequestParam(required = false) String buscar,
                                         @RequestParam(required = false) LocalDate desde,
                                         @RequestParam(required = false) LocalDate hasta,
                                         Pageable pageable, Authentication authentication) {
        Long currentUserId = Long.valueOf(authentication.getPrincipal().toString());
        return tareaUseCase.listar(proyectoId, null, currentUserId, estado, prioridad, buscar, desde, hasta, pageable)
                .map(TareaConsultaController::toResponse);
    }

    private static TareaResponse toResponse(Tarea t) {
        return new TareaResponse(t.getId(), t.getTitulo(), t.getDescripcion(), t.getProyectoId(), t.getActividadId(),
                t.getAsignadoA(), t.getEstado(), t.getPrioridad(), t.getFechaLimite(), t.getEtiquetas(),
                t.getFechaCreacion(), t.getFechaActualizacion());
    }
}
