package com.tuapp.finanzas.task.domain.model;

import com.tuapp.finanzas.task.domain.exception.TareaInvalidaException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class Tarea {

    private final Long id;
    private String titulo;
    private String descripcion;
    private Long actividadId;
    private Long proyectoId;
    private Long asignadoA;
    private EstadoTarea estado;
    private PrioridadTarea prioridad;
    private LocalDate fechaLimite;
    private List<String> etiquetas;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Tarea(Long id, String titulo, String descripcion, Long actividadId, Long proyectoId, Long asignadoA,
                  EstadoTarea estado, PrioridadTarea prioridad,
                  LocalDate fechaLimite, List<String> etiquetas,
                  Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.actividadId = actividadId;
        this.proyectoId = proyectoId;
        this.asignadoA = asignadoA;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaLimite = fechaLimite;
        this.etiquetas = etiquetas == null ? List.of() : List.copyOf(etiquetas);
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Tarea crear(String titulo, String descripcion, Long actividadId, Long proyectoId,
                              Long asignadoA, EstadoTarea estado, PrioridadTarea prioridad,
                              LocalDate fechaLimite, List<String> etiquetas) {
        validarTitulo(titulo);
        if (actividadId == null) {
            throw new TareaInvalidaException("La tarea debe pertenecer a una actividad");
        }
        validarFechas(fechaInicio, fechaFin);
        Instant ahora = Instant.now();
        PrioridadTarea prioridadFinal = prioridad != null ? prioridad : PrioridadTarea.MEDIA;
        return new Tarea(null, titulo, descripcion, actividadId, proyectoId, asignadoA,
            estado == null ? EstadoTarea.BACKLOG : estado, prioridadFinal,
            fechaLimite, etiquetas, ahora, ahora);
    }

        public static Tarea reconstruir(Long id, String titulo, String descripcion, Long actividadId, Long proyectoId,
                        Long asignadoA, EstadoTarea estado, PrioridadTarea prioridad,
                        LocalDate fechaLimite, List<String> etiquetas,
                        Instant fechaCreacion, Instant fechaActualizacion) {
        return new Tarea(id, titulo, descripcion, actividadId, proyectoId, asignadoA, estado, prioridad,
            fechaLimite, etiquetas, fechaCreacion, fechaActualizacion);
    }

    public void actualizar(String nuevoTitulo, String nuevaDescripcion) {
        validarTitulo(nuevoTitulo);
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.fechaActualizacion = Instant.now();
    }

    public void reprogramar(Instant nuevaFechaInicio, Instant nuevaFechaFin) {
        validarFechas(nuevaFechaInicio, nuevaFechaFin);
        this.fechaInicio = nuevaFechaInicio;
        this.fechaFin = nuevaFechaFin;
        this.fechaActualizacion = Instant.now();
    }

    public void cambiarEstado(EstadoTarea nuevoEstado) {
        if (nuevoEstado == null) {
            throw new TareaInvalidaException("El estado no puede ser nulo");
        }
        this.estado = nuevoEstado;
        this.fechaActualizacion = Instant.now();
    }

    public void asignar(Long usuarioId) {
        this.asignadoA = usuarioId;
        this.fechaActualizacion = Instant.now();
    }

    public void cambiarPrioridad(PrioridadTarea nuevaPrioridad) {
        if (nuevaPrioridad == null) {
            throw new TareaInvalidaException("La prioridad no puede ser nula");
        }
        this.prioridad = nuevaPrioridad;
        this.fechaActualizacion = Instant.now();
    }

    public void cambiarFechaLimite(LocalDate nuevaFechaLimite) {
        this.fechaLimite = nuevaFechaLimite;
        this.fechaActualizacion = Instant.now();
    }

    public void actualizarDetalles(String nuevoTitulo, String nuevaDescripcion, LocalDate nuevaFechaLimite,
                                   List<String> nuevasEtiquetas) {
        validarTitulo(nuevoTitulo);
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.fechaLimite = nuevaFechaLimite;
        this.etiquetas = nuevasEtiquetas == null ? List.of() : List.copyOf(nuevasEtiquetas);
        this.fechaActualizacion = Instant.now();
    }

    public void reemplazar(String nuevoTitulo, String nuevaDescripcion, Long nuevaActividadId, Long nuevoProyectoId,
                           Long nuevoAsignadoA, EstadoTarea nuevoEstado, PrioridadTarea nuevaPrioridad,
                           LocalDate nuevaFechaLimite, List<String> nuevasEtiquetas) {
        validarTitulo(nuevoTitulo);
        this.titulo = nuevoTitulo;
        this.descripcion = nuevaDescripcion;
        this.actividadId = nuevaActividadId;
        this.proyectoId = nuevoProyectoId;
        this.asignadoA = nuevoAsignadoA;
        if (nuevoEstado != null) this.estado = nuevoEstado;
        if (nuevaPrioridad != null) this.prioridad = nuevaPrioridad;
        this.fechaLimite = nuevaFechaLimite;
        this.etiquetas = nuevasEtiquetas == null ? List.of() : List.copyOf(nuevasEtiquetas);
        this.fechaActualizacion = Instant.now();
    }

    private static void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new TareaInvalidaException("El título de la tarea no puede estar vacío");
        }
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
        this.fechaActualizacion = Instant.now();
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
        this.fechaActualizacion = Instant.now();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Long getActividadId() { return actividadId; }
    public Long getProyectoId() { return proyectoId; }
    public Long getAsignadoA() { return asignadoA; }
    public EstadoTarea getEstado() { return estado; }
    public PrioridadTarea getPrioridad() { return prioridad; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public List<String> getEtiquetas() { return etiquetas; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}