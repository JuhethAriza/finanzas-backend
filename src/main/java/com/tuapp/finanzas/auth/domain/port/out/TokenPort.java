package com.tuapp.finanzas.auth.domain.port.out;

import java.util.List;

public interface TokenPort {
    record TokenData(Long usuarioId, List<String> roles) {}

    String generarToken(Long usuarioId, String email, List<String> roles);
    TokenData validarYObtenerDatos(String token);
}
