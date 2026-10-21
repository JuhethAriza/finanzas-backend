package com.tuapp.finanzas.user.adapter;

import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import com.tuapp.finanzas.user.entity.UsuarioEntity;
import com.tuapp.finanzas.user.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryJpaAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryJpaAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getPasswordHash());
        UsuarioEntity guardada = jpaRepository.save(entity);
        return Usuario.reconstruir(guardada.getId(), guardada.getNombre(), guardada.getEmail(), guardada.getPasswordHash());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(e -> Usuario.reconstruir(e.getId(), e.getNombre(), e.getEmail(), e.getPasswordHash()));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email).map(e -> Usuario.reconstruir(e.getId(), e.getNombre(), e.getEmail(), e.getPasswordHash()));
    }

    @Override
    public List<Usuario> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(e -> Usuario.reconstruir(e.getId(), e.getNombre(), e.getEmail(), e.getPasswordHash()))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}
