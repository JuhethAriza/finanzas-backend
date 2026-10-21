package com.tuapp.finanzas.auth.dto.response;

import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.dto.response.UsuarioResponse;

public record LoginResponse(String token, String tipo, UsuarioResponse usuario) {

    public static LoginResponse of(String token, Usuario usuario) {
        return new LoginResponse(token, "Bearer", UsuarioResponse.from(usuario));
    }
}