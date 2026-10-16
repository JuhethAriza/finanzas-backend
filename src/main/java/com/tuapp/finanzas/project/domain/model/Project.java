package com.tuapp.finanzas.project.domain.model;

import com.tuapp.finanzas.project.domain.exception.ProyectoInvalidoException;

import java.time.Instant;

public class Project {

    private final Long id;
    private String nombre;
    private String descripcion;
    private final Long teamId;
    private boolean activo;
    private Instant fechaInicio;
    private Instant fechaFin;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Project(Long id, String nombre, String descripcion, Long teamId, boolean activo,
                    Instant fechaInicio, Instant fechaFin,
                    Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.teamId = teamId;
        this.activo = activo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Project crear(String nombre, String descripcion, Long teamId,
                                Instant fechaInicio, Instant fechaFin) {
        validarNombre(nombre);
        if (teamId == null) {
            throw new ProyectoInvalidoException("El proyecto debe pertenecer a un equipo");
        }
        validarFechas(fechaInicio, fechaFin);
        Instant ahora = Instant.now();
        return new Project(null, nombre, descripcion, teamId, true, fechaInicio, fechaFin, ahora, ahora);
    }

    public static Project reconstruir(Long id, String nombre, String descripcion, Long teamId, boolean activo,
                                      Instant fechaInicio, Instant fechaFin,
                                      Instant fechaCreacion, Instant fechaActualizacion) {
        return new Project(id, nombre, descripcion, teamId, activo, fechaInicio, fechaFin,
                fechaCreacion, fechaActualizacion);
    }

    public void actualizar(String nuevoNombre, String nuevaDescripcion) {
        validarNombre(nuevoNombre);
        this.nombre = nuevoNombre;
        this.descripcion = nuevaDescripcion;
        this.fechaActualizacion = Instant.now();
    }

    public void reprogramar(Instant nuevaFechaInicio, Instant nuevaFechaFin) {
        validarFechas(nuevaFechaInicio, nuevaFechaFin);
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFin = nuevaFechaFin;
        this.fechaActualizacion = Instant.now();
    }

    public void archivar() {
        this.activo = false;
        this.fechaActualizacion = Instant.now();
    }

    public void activar() {
        this.activo = true;
        this.fechaActualizacion = Instant.now();
    }

    private static void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ProyectoInvalidoException("El nombre del proyecto no puede estar vacío");
        }
    }

    private static void validarFechas(Instant fechaInicio, Instant fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new ProyectoInvalidoException("El proyecto debe tener fecha de inicio y de fin");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new ProyectoInvalidoException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Long getTeamId() { return teamId; }
    public boolean isActivo() { return activo; }
    public Instant getFechaInicio() { return fechaInicio; }
    public Instant getFechaFin() { return fechaFin; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}