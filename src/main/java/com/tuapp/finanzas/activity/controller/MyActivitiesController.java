package com.tuapp.finanzas.activity.controller;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import com.tuapp.finanzas.activity.domain.port.in.ActividadUseCase;
import com.tuapp.finanzas.activity.dto.response.ActividadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-activities")
public class MyActivitiesController {

    private final ActividadUseCase actividadUseCase;

    public MyActivitiesController(ActividadUseCase actividadUseCase) {
        this.actividadUseCase = actividadUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<ActividadResponse>> listarMisActividades(
            Authentication authentication,
            Pageable pageable) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }

        Long usuarioId = (Long) authentication.getPrincipal();
        Page<ActividadResponse> pagina = actividadUseCase.listarPorUsuario(usuarioId, pageable)
                .map(this::toResponse);

        return ResponseEntity.ok(pagina);
    }

    private ActividadResponse toResponse(Actividad a) {
        return new ActividadResponse(a.getId(), a.getNombre(), a.getDescripcion(), a.getProyectoId(),
                a.getTotalTasks(), a.getCompletedTasks(), a.getFechaCreacion(), a.getFechaActualizacion());
    }
}
