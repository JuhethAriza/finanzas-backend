package com.tuapp.finanzas.user.dto.response;

import com.tuapp.finanzas.user.domain.model.Usuario;

public record UsuarioResponse(Long id, String nombre, String email) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}