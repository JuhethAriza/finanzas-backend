package com.tuapp.finanzas.project.domain.model;

import java.time.Instant;

public class ProyectoUsuario {
    private final Long id;
    private final Long proyectoId;
    private final Long usuarioId;
    private final Instant fechaAsignacion;

    private ProyectoUsuario(Long id, Long proyectoId, Long usuarioId, Instant fechaAsignacion) {
        this.id = id;
        this.proyectoId = proyectoId;
        this.usuarioId = usuarioId;
        this.fechaAsignacion = fechaAsignacion;
    }

    public static ProyectoUsuario crear(Long proyectoId, Long usuarioId) {
        if (proyectoId == null || usuarioId == null) {
            throw new IllegalArgumentException("El proyectoId y usuarioId son obligatorios");
        }
        return new ProyectoUsuario(null, proyectoId, usuarioId, java.time.Instant.now());
    }

    public static ProyectoUsuario reconstruir(Long id, Long proyectoId, Long usuarioId, Instant fechaAsignacion) {
        return new ProyectoUsuario(id, proyectoId, usuarioId, fechaAsignacion);
    }

    public Long getId() { return id; }
    public Long getProyectoId() { return proyectoId; }
    public Long getUsuarioId() { return usuarioId; }
    public Instant getFechaAsignacion() { return fechaAsignacion; }
}
