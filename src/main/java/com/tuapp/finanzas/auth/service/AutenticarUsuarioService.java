package com.tuapp.finanzas.auth.service;

import com.tuapp.finanzas.auth.domain.exception.CredencialesInvalidasException;
import com.tuapp.finanzas.auth.domain.port.in.AutenticarUsuarioUseCase;
import com.tuapp.finanzas.auth.domain.port.out.GoogleTokenVerifierPort;
import com.tuapp.finanzas.auth.domain.port.out.GoogleTokenVerifierPort.GooglePayload;
import com.tuapp.finanzas.auth.domain.port.out.TokenPort;
import com.tuapp.finanzas.user.domain.exception.UsuarioNoEncontradoException;
import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.domain.port.out.PasswordEncoderPort;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class AutenticarUsuarioService implements AutenticarUsuarioUseCase {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final UsuarioRepositoryPort repository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;
    private final GoogleTokenVerifierPort googleVerifier;

    public AutenticarUsuarioService(
            UsuarioRepositoryPort repository,
            PasswordEncoderPort passwordEncoder,
            TokenPort tokenPort,
            GoogleTokenVerifierPort googleVerifier
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
        this.googleVerifier = googleVerifier;
    }

    @Override
    public ResultadoLogin login(String email, String passwordPlano) {
        Usuario usuario = repository.buscarPorEmail(email)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.coincide(passwordPlano, usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException();
        }

        String token = tokenPort.generarToken(usuario.getId(), usuario.getEmail(), java.util.List.of());
        return new ResultadoLogin(token, usuario);
    }

    @Override
    public ResultadoLogin loginConGoogle(String idToken) {
        GooglePayload payload = googleVerifier.verificar(idToken);

        Optional<Usuario> existente = repository.buscarPorEmail(payload.email());
        Usuario usuario = existente.orElseGet(() ->
                repository.guardar(Usuario.crear(
                        payload.name() != null ? payload.name() : payload.email(),
                        payload.email(),
                        passwordEncoder.encriptar(generarPasswordAleatorio())
                ))
        );

        String token = tokenPort.generarToken(usuario.getId(), usuario.getEmail(), java.util.List.of());
        return new ResultadoLogin(token, usuario);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    private static String generarPasswordAleatorio() {
        byte[] bytes = new byte[48];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
