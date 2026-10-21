package com.tuapp.finanzas.team.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "team_members", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "user_id"})
})
public class TeamMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String rol;

    @Column(name = "fecha_ingreso", nullable = false)
    private Instant fechaIngreso;

    protected TeamMemberEntity() {}

    public TeamMemberEntity(Long id, Long teamId, Long userId, String rol, Instant fechaIngreso) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getId() { return id; }
    public Long getTeamId() { return teamId; }
    public Long getUserId() { return userId; }
    public String getRol() { return rol; }
    public Instant getFechaIngreso() { return fechaIngreso; }
}
