package com.tuapp.finanzas.project.controller;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import com.tuapp.finanzas.project.domain.model.ProyectoUsuario;
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;
import com.tuapp.finanzas.project.domain.port.in.ProyectoUseCase;
import com.tuapp.finanzas.project.dto.request.*;
import com.tuapp.finanzas.project.dto.response.ProyectoResponse;
import com.tuapp.finanzas.project.dto.response.ProyectoUsuarioResponse;
import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProyectoController {

    private final ProyectoUseCase proyectoUseCase;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ProyectoController(ProyectoUseCase proyectoUseCase, UsuarioRepositoryPort usuarioRepositoryPort) {
        this.proyectoUseCase = proyectoUseCase;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @GetMapping
    public ResponseEntity<Page<ProyectoResponse>> listar(Pageable pageable) {
        Page<ProyectoResponse> pagina = proyectoUseCase.listar(pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest request) {
        Proyecto proyecto = proyectoUseCase.crear(request.nombre(), request.descripcion(),
                                               request.equipoId(), request.responsableId(),
                                               request.estado(), request.fechaInicio(), request.fechaFin());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(proyecto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> obtener(@PathVariable Long id) {
        return proyectoUseCase.obtenerPorId(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ProyectoActualizarRequest request) {
        Proyecto proyecto = proyectoUseCase.actualizar(id, request.nombre(), request.descripcion(),
                                                   request.estado(), request.fechaInicio(),
                                                   request.fechaFin(), request.responsableId(),
                                                   request.equipoId());
        return ResponseEntity.ok(toResponse(proyecto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<Void> asignarUsuario(@PathVariable Long id, @RequestParam Long userId) {
        proyectoUseCase.asignarUsuario(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<Void> desasignarUsuario(@PathVariable Long id, @PathVariable Long userId) {
        proyectoUseCase.desasignarUsuario(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<ProyectoUsuarioResponse>> listarUsuarios(@PathVariable Long id) {
        List<ProyectoUsuario> usuarios = proyectoUseCase.listarUsuarios(id);

        // Resolve user names and emails
        Map<Long, Usuario> userMap = usuarios.stream()
                .map(ProyectoUsuario::getUsuarioId)
                .distinct()
                .map(uid -> usuarioRepositoryPort.buscarPorId(uid).orElse(null))
                .filter(u -> u != null)
                .collect(Collectors.toMap(Usuario::getId, u -> u));

        List<ProyectoUsuarioResponse> responses = usuarios.stream()
                .map(pu -> {
                    Usuario user = userMap.get(pu.getUsuarioId());
                    String nombre = user != null ? user.getNombre() : null;
                    String email = user != null ? user.getEmail() : null;
                    return new ProyectoUsuarioResponse(pu.getId(), pu.getProyectoId(),
                            pu.getUsuarioId(), nombre, email, pu.getFechaAsignacion());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam ProyectoEstado estado) {
        proyectoUseCase.cambiarEstado(id, estado);
        return ResponseEntity.noContent().build();
    }

    private ProyectoResponse toResponse(Proyecto p) {
        return new ProyectoResponse(p.getId(), p.getNombre(), p.getDescripcion(),
                p.getEstado(), p.getResponsableId(), p.getEquipoId(),
                p.getFechaInicio(), p.getFechaFin(),
                p.getFechaCreacion(), p.getFechaActualizacion());
    }
}
