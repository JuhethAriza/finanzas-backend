package com.tuapp.finanzas.team.domain.model;

import java.time.Instant;

public class TeamMember {

    private final Long id;
    private final Long teamId;
    private final Long userId;
    private final String rol;
    private final Instant fechaIngreso;

    private TeamMember(Long id, Long teamId, Long userId, String rol, Instant fechaIngreso) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
    }

    public static TeamMember crear(Long teamId, Long userId, String rol) {
        String rolFinal = (rol == null || rol.isBlank()) ? "MIEMBRO" : rol;
        return new TeamMember(null, teamId, userId, rolFinal, Instant.now());
    }

    public static TeamMember reconstruir(Long id, Long teamId, Long userId, String rol, Instant fechaIngreso) {
        return new TeamMember(id, teamId, userId, rol, fechaIngreso);
    }

    public Long getId() { return id; }
    public Long getTeamId() { return teamId; }
    public Long getUserId() { return userId; }
    public String getRol() { return rol; }
    public Instant getFechaIngreso() { return fechaIngreso; }
}