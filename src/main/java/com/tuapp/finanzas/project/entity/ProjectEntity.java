package com.tuapp.finanzas.project.entity;

import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "proyectos")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "responsable_id")
    private Long responsableId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus estado;

    @Column(name = "fecha_inicio", nullable = false)
    private Instant fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Instant fechaFin;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    protected ProjectEntity() {}

    public ProjectEntity(Long id, String nombre, String descripcion, Long teamId, Long responsableId,
                         ProjectStatus estado, Instant fechaInicio, Instant fechaFin,
                         Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.teamId = teamId;
        this.responsableId = responsableId;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Long getTeamId() { return teamId; }
    public Long getResponsableId() { return responsableId; }
    public ProjectStatus getEstado() { return estado; }
    public Instant getFechaInicio() { return fechaInicio; }
    public Instant getFechaFin() { return fechaFin; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}
