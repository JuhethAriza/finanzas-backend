package com.tuapp.finanzas.user.controller;

import com.tuapp.finanzas.user.dto.request.ActualizarNombreRequest;
import com.tuapp.finanzas.user.dto.request.CrearUsuarioRequest;
import com.tuapp.finanzas.user.dto.response.UsuarioResponse;
import com.tuapp.finanzas.user.domain.port.in.UsuarioUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody @Valid CrearUsuarioRequest request) {
        var usuario = usuarioUseCase.crear(request.nombre(), request.email(), request.password());
        return ResponseEntity.status(201).body(UsuarioResponse.from(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioResponse.from(usuarioUseCase.obtenerPorId(id)));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        var usuarios = usuarioUseCase.listarTodos().stream()
                .map(UsuarioResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarNombre(@PathVariable Long id,
            @RequestBody @Valid ActualizarNombreRequest request) {
        return ResponseEntity.ok(UsuarioResponse.from(usuarioUseCase.actualizarNombre(id, request.nombre())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
