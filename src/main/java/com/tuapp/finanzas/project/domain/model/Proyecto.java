package com.tuapp.finanzas.project.domain.model;

import java.time.Instant;
import java.time.LocalDate;
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;

public class Proyecto {
    private final Long id;
    private String nombre;
    private String descripcion;
    private ProyectoEstado estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long responsableId;
    private Long equipoId;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Proyecto(Long id, String nombre, String descripcion, ProyectoEstado estado,
                     LocalDate fechaInicio, LocalDate fechaFin, Long responsableId, Long equipoId,
                     Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.responsableId = responsableId;
        this.equipoId = equipoId;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Proyecto crear(String nombre, String descripcion, Long equipoId, Long responsableId,
                                  ProyectoEstado estado, LocalDate fechaInicio, LocalDate fechaFin) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        Instant ahora = Instant.now();
        ProyectoEstado estadoFinal = estado != null ? estado : ProyectoEstado.ACTIVO;
        return new Proyecto(null, nombre, descripcion, estadoFinal,
                fechaInicio, fechaFin, responsableId, equipoId, ahora, ahora);
    }

    public static Proyecto reconstruir(Long id, String nombre, String descripcion, ProyectoEstado estado,
                                       LocalDate fechaInicio, LocalDate fechaFin, Long responsableId, Long equipoId,
                                       Instant fechaCreacion, Instant fechaActualizacion) {
        return new Proyecto(id, nombre, descripcion, estado, fechaInicio, fechaFin, responsableId, equipoId, fechaCreacion, fechaActualizacion);
    }

    public void actualizar(String nuevoNombre, String nuevaDescripcion, ProyectoEstado nuevoEstado,
                            LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin, Long nuevoResponsableId, Long nuevoEquipoId) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        this.nombre = nuevoNombre;
        this.descripcion = nuevaDescripcion;
        this.estado = nuevoEstado;
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFin = nuevaFechaFin;
        this.responsableId = nuevoResponsableId;
        if (nuevoEquipoId != null) {
            this.equipoId = nuevoEquipoId;
        }
        this.fechaActualizacion = Instant.now();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public ProyectoEstado getEstado() { return estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public Long getResponsableId() { return responsableId; }
    public Long getEquipoId() { return equipoId; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}
