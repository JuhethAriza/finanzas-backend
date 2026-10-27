package com.tuapp.finanzas.activity.domain.model;

import java.time.Instant;

public class Actividad {
    private final Long id;
    private String nombre;
    private String descripcion;
    private final Long proyectoId;
    private int totalTasks;
    private int completedTasks;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Actividad(Long id, String nombre, String descripcion, Long proyectoId,
                      int totalTasks, int completedTasks, Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.proyectoId = proyectoId;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Actividad crear(String nombre, String descripcion, Long proyectoId) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la actividad no puede estar vacío");
        }
        if (proyectoId == null) {
            throw new IllegalArgumentException("La actividad debe pertenecer a un proyecto");
        }
        Instant ahora = Instant.now();
        return new Actividad(null, nombre, descripcion, proyectoId, 0, 0, ahora, ahora);
    }

    public static Actividad reconstruir(Long id, String nombre, String descripcion, Long proyectoId,
                                       int totalTasks, int completedTasks, Instant fechaCreacion, Instant fechaActualizacion) {
        return new Actividad(id, nombre, descripcion, proyectoId, totalTasks, completedTasks, fechaCreacion, fechaActualizacion);
    }

    public void actualizar(String nuevoNombre, String nuevaDescripcion) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la actividad no puede estar vacío");
        }
        this.nombre = nuevoNombre;
        this.descripcion = nuevaDescripcion;
        this.fechaActualizacion = java.time.Instant.now();
    }

    public void incrementTotalTasks() {
        this.totalTasks++;
        this.fechaActualizacion = java.time.Instant.now();
    }

    public void decrementTotalTasks() {
        if (this.totalTasks > 0) {
            this.totalTasks--;
            this.fechaActualizacion = java.time.Instant.now();
        }
    }

    public void incrementCompletedTasks() {
        this.completedTasks++;
        this.fechaActualizacion = java.time.Instant.now();
    }

    public void decrementCompletedTasks() {
        if (this.completedTasks > 0) {
            this.completedTasks--;
            this.fechaActualizacion = java.time.Instant.now();
        }
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Long getProyectoId() { return proyectoId; }
    public int getTotalTasks() { return totalTasks; }
    public int getCompletedTasks() { return completedTasks; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}
