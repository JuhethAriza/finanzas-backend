package com.tuapp.finanzas.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearUsuarioRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6, max = 64, message = "La contraseña debe tener entre 6 y 64 caracteres") String password
) {}
