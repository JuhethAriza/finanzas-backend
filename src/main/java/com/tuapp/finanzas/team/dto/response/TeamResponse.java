package com.tuapp.finanzas.team.dto.response;

import com.tuapp.finanzas.team.domain.model.Team;

import java.time.Instant;

public record TeamResponse(Long id, String nombre, String descripcion, Instant fechaCreacion) {
    public static TeamResponse from(Team team) {
        return new TeamResponse(team.getId(), team.getNombre(), team.getDescripcion(), team.getFechaCreacion());
    }
}