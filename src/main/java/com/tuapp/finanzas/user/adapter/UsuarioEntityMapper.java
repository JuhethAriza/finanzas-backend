package com.tuapp.finanzas.user.adapter;

import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        return new UsuarioEntity(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getPasswordHash());
    }

    public Usuario toDomain(UsuarioEntity entity) {
        return Usuario.reconstruir(entity.getId(), entity.getNombre(), entity.getEmail(), entity.getPasswordHash());
    }
}
