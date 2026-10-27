package com.tuapp.finanzas.project.dto.response;

import java.time.Instant;

public record MiembroResponse(Long usuarioId, Instant fechaIngreso) {}