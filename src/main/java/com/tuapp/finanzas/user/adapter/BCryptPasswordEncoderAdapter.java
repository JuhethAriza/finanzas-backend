package com.tuapp.finanzas.user.adapter;

import com.tuapp.finanzas.user.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encriptar(String passwordPlano) {
        return encoder.encode(passwordPlano);
    }

    @Override
    public boolean coincide(String passwordPlano, String passwordHash) {
        return encoder.matches(passwordPlano, passwordHash);
    }
}
