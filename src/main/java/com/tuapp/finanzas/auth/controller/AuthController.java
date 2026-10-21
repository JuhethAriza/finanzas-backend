package com.tuapp.finanzas.auth.controller;

import com.tuapp.finanzas.auth.domain.port.in.AutenticarUsuarioUseCase;
import com.tuapp.finanzas.auth.dto.request.GoogleLoginRequest;
import com.tuapp.finanzas.auth.dto.request.LoginRequest;
import com.tuapp.finanzas.auth.dto.response.LoginResponse;
import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUseCase;

    public AuthController(AutenticarUsuarioUseCase autenticarUseCase) {
        this.autenticarUseCase = autenticarUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var resultado = autenticarUseCase.login(request.email(), request.password());
        return ResponseEntity.ok(LoginResponse.of(resultado.token(), resultado.usuario()));
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> loginGoogle(@RequestBody @Valid GoogleLoginRequest request) {
        var resultado = autenticarUseCase.loginConGoogle(request.idToken());
        return ResponseEntity.ok(LoginResponse.of(resultado.token(), resultado.usuario()));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }
        Long usuarioId = (Long) authentication.getPrincipal();
        Usuario usuario = autenticarUseCase.obtenerPorId(usuarioId);
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }
}
