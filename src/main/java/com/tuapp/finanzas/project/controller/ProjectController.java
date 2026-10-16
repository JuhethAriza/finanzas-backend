package com.tuapp.finanzas.project.controller;

import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.port.in.ProjectUseCase;
import com.tuapp.finanzas.project.dto.request.*;
import com.tuapp.finanzas.project.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectUseCase proyectoUseCase;

    public ProjectController(ProjectUseCase proyectoUseCase) {
        this.proyectoUseCase = proyectoUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> listar(
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) Boolean activo,
            Pageable pageable) {
        Page<ProjectResponse> pagina = proyectoUseCase.listar(teamId, activo, pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> crear(@Valid @RequestBody ProjectRequest request) {
        Project proyecto = proyectoUseCase.crear(request.nombre(), request.descripcion(),
                request.teamId(), request.fechaInicio(), request.fechaFin());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(proyecto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(proyectoUseCase.obtenerPorId(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> actualizar(@PathVariable Long id,
                                                      @RequestBody ProjectActualizarRequest request) {
        return ResponseEntity.ok(toResponse(proyectoUseCase.actualizar(id, request.nombre(), request.descripcion())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MiembroResponse>> listarMiembros(@PathVariable Long id) {
        List<MiembroResponse> miembros = proyectoUseCase.listarMiembros(id).stream()
                .map(m -> new MiembroResponse(m.usuarioId(), m.fechaIngreso()))
                .toList();
        return ResponseEntity.ok(miembros);
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<Void> agregarMiembro(@PathVariable Long id, @Valid @RequestBody MiembroRequest request) {
        proyectoUseCase.agregarMiembro(id, request.usuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/members/{usuarioId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id, @PathVariable Long usuarioId) {
        proyectoUseCase.eliminarMiembro(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ProjectStatsResponse> obtenerStats(@PathVariable Long id) {
        var stats = proyectoUseCase.obtenerStats(id);
        return ResponseEntity.ok(new ProjectStatsResponse(
                stats.proyectoId(), stats.totalTareas(), stats.tareasCompletadas(),
                stats.porcentajeProgreso(), stats.tareasPorEstado()));
    }

    private ProjectResponse toResponse(Project p) {
        return new ProjectResponse(p.getId(), p.getNombre(), p.getDescripcion(), p.getTeamId(),
                p.isActivo(), p.getFechaInicio(), p.getFechaFin(),
                p.getFechaCreacion(), p.getFechaActualizacion());
    }
}
