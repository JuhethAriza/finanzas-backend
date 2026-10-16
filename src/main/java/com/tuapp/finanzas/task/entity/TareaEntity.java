package com.tuapp.finanzas.task.entity;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tareas")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "actividad_id", nullable = false)
    private Long actividadId;

    @Column(name = "proyecto_id")
    private Long proyectoId;

    @Column(name = "asignado_a")
    private Long asignadoA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTarea estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioridadTarea prioridad;

    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @ElementCollection
    @CollectionTable(name = "tarea_etiquetas", joinColumns = @JoinColumn(name = "tarea_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas = new ArrayList<>();

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    protected TareaEntity() {}

    public TareaEntity(Long id, String titulo, String descripcion, Long actividadId, Long proyectoId, Long asignadoA,
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
        this.etiquetas = etiquetas == null ? new ArrayList<>() : new ArrayList<>(etiquetas);
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
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