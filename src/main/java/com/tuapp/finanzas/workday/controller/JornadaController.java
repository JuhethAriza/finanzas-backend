package com.tuapp.finanzas.workday.controller;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import com.tuapp.finanzas.workday.domain.port.in.JornadaUseCase;
import com.tuapp.finanzas.workday.dto.request.*;
import com.tuapp.finanzas.workday.dto.response.JornadaResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks/{taskId}/workdays")
public class JornadaController {

    private final JornadaUseCase jornadaUseCase;

    public JornadaController(JornadaUseCase jornadaUseCase) {
        this.jornadaUseCase = jornadaUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<JornadaResponse>> listar(@PathVariable Long taskId, Pageable pageable) {
        Page<JornadaResponse> pagina = jornadaUseCase.listarPorTareaId(taskId, pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<JornadaResponse> crear(@PathVariable Long taskId, @Valid @RequestBody JornadaRequest request) {
        Jornada jornada = jornadaUseCase.crear(taskId, request.fecha(), request.horasTrabajadas(), request.notas());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(jornada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JornadaResponse> obtener(@PathVariable Long id) {
        return jornadaUseCase.obtenerPorId(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JornadaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody JornadaActualizarRequest request) {
        Jornada jornada = jornadaUseCase.actualizar(id, request.fecha(), request.horasTrabajadas(), request.notas());
        return ResponseEntity.ok(toResponse(jornada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        jornadaUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private JornadaResponse toResponse(Jornada j) {
        return new JornadaResponse(j.getId(), j.getTareaId(), j.getFecha(), j.getHorasTrabajadas(),
                j.getNotas(), j.getFechaCreacion(), j.getFechaActualizacion());
    }
}
