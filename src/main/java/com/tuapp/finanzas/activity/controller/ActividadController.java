package com.tuapp.finanzas.activity.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import com.tuapp.finanzas.activity.domain.port.in.ActividadUseCase;
import com.tuapp.finanzas.activity.dto.request.ActividadActualizarRequest;
import com.tuapp.finanzas.activity.dto.request.ActividadRequest;
import com.tuapp.finanzas.activity.dto.response.ActividadResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects/{projectId}/activities")
public class ActividadController {

    private final ActividadUseCase actividadUseCase;

    public ActividadController(ActividadUseCase actividadUseCase) {
        this.actividadUseCase = actividadUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<ActividadResponse>> listar(
            @PathVariable Long projectId,
            Pageable pageable) {
        Page<ActividadResponse> pagina = actividadUseCase.listar(projectId, pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<ActividadResponse> crear(
            @PathVariable Long projectId,
            @Valid @RequestBody ActividadRequest request) {
        Actividad actividad = actividadUseCase.crear(request.nombre(), request.descripcion(), projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(actividad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadResponse> obtener(@PathVariable Long id) {
        return actividadUseCase.obtenerPorId(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActividadResponse> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ActividadActualizarRequest request) {
        Actividad actividad = actividadUseCase.actualizar(id, request.nombre(), request.descripcion());
        return ResponseEntity.ok(toResponse(actividad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ActividadResponse toResponse(Actividad a) {
        return new ActividadResponse(a.getId(), a.getNombre(), a.getDescripcion(), a.getProyectoId(),
                a.getTotalTasks(), a.getCompletedTasks(), a.getFechaCreacion(), a.getFechaActualizacion());
    }
}
