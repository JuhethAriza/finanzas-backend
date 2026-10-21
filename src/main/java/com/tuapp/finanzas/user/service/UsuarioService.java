package com.tuapp.finanzas.user.service;

import com.tuapp.finanzas.user.domain.exception.EmailYaRegistradoException;
import com.tuapp.finanzas.user.domain.exception.UsuarioNoEncontradoException;
import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.domain.port.in.UsuarioUseCase;
import com.tuapp.finanzas.user.domain.port.out.PasswordEncoderPort;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepositoryPort repository;
    private final PasswordEncoderPort passwordEncoder;

    public UsuarioService(UsuarioRepositoryPort repository, PasswordEncoderPort passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crear(String nombre, String email, String passwordPlano) {
        repository.buscarPorEmail(email).ifPresent(u -> {
            throw new EmailYaRegistradoException();
        });
        String passwordHash = passwordEncoder.encriptar(passwordPlano);
        Usuario usuario = Usuario.crear(nombre, email, passwordHash);
        return repository.guardar(usuario);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public Usuario actualizarNombre(Long id, String nuevoNombre) {
        Usuario usuario = obtenerPorId(id);
        usuario.actualizarNombre(nuevoNombre);
        return repository.guardar(usuario);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        repository.eliminar(id);
    }
}
