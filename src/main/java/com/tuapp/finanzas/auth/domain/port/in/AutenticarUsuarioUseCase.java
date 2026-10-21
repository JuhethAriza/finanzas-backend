package com.tuapp.finanzas.auth.domain.port.in;

import com.tuapp.finanzas.user.domain.model.Usuario;

public interface AutenticarUsuarioUseCase {
    ResultadoLogin login(String email, String passwordPlano);
    ResultadoLogin loginConGoogle(String idToken);
    Usuario obtenerPorId(Long id);

    record ResultadoLogin(String token, Usuario usuario) {}
}
