package com.tuapp.finanzas.user.domain.port.out;

public interface PasswordEncoderPort {
    String encriptar(String passwordPlano);
    boolean coincide(String passwordPlano, String passwordHash);
}
