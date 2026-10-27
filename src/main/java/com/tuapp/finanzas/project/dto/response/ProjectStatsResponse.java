package com.tuapp.finanzas.project.dto.response;

import java.util.Map;

public record ProjectStatsResponse(
        Long proyectoId,
        long totalTareas,
        long tareasCompletadas,
        double porcentajeProgreso,
        Map<String, Long> tareasPorEstado
) {}