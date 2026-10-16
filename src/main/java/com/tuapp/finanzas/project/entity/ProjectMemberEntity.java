package com.tuapp.finanzas.project.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "proyecto_miembros", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"proyecto_id", "usuario_id"})
})
public class ProjectMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proyecto_id", nullable = false)
    private Long proyectoId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "fecha_ingreso", nullable = false)
    private Instant fechaIngreso;

    protected ProjectMemberEntity() {}

    public ProjectMemberEntity(Long proyectoId, Long usuarioId, Instant fechaIngreso) {
        this.proyectoId = proyectoId;
        this.usuarioId = usuarioId;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getId() { return id; }
    public Long getProyectoId() { return proyectoId; }
    public Long getUsuarioId() { return usuarioId; }
    public Instant getFechaIngreso() { return fechaIngreso; }
}
