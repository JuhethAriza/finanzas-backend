package com.tuapp.finanzas.team.domain.model;

import java.time.Instant;

public class Team {

    private final Long id;
    private String nombre;
    private String descripcion;
    private final Instant fechaCreacion;

    private Team(Long id, String nombre, String descripcion, Instant fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public static Team crear(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío");
        }
        return new Team(null, nombre, descripcion, Instant.now());
    }

    public static Team reconstruir(Long id, String nombre, String descripcion, Instant fechaCreacion) {
        return new Team(id, nombre, descripcion, fechaCreacion);
    }

    public void actualizar(String nuevoNombre, String nuevaDescripcion) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío");
        }
        this.nombre = nuevoNombre;
        this.descripcion = nuevaDescripcion;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Instant getFechaCreacion() { return fechaCreacion; }
}