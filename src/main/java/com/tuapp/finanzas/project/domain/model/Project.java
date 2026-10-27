package com.tuapp.finanzas.project.domain.model;

import com.tuapp.finanzas.project.domain.exception.ProyectoInvalidoException;
import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;

import java.time.Instant;

public class Project {

    private final Long id;
    private String nombre;
    private String descripcion;
    private final Long teamId;
    private Long responsableId;
    private ProjectStatus estado;
    private Instant fechaInicio;
    private Instant fechaFin;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Project(Long id, String nombre, String descripcion, Long teamId, Long responsableId,
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

    public static Project crear(String nombre, String descripcion, Long teamId, Long responsableId,
                                ProjectStatus estado, Instant fechaInicio, Instant fechaFin) {
        validarNombre(nombre);
        if (teamId == null) {
            throw new ProyectoInvalidoException("El proyecto debe pertenecer a un equipo");
        }
        validarFechas(fechaInicio, fechaFin);
        Instant ahora = Instant.now();
        ProjectStatus estadoFinal = estado != null ? estado : ProjectStatus.ACTIVO;
        return new Project(null, nombre, descripcion, teamId, responsableId, estadoFinal,
                fechaInicio, fechaFin, ahora, ahora);
    }

    public static Project reconstruir(Long id, String nombre, String descripcion, Long teamId,
                                      Long responsableId, ProjectStatus estado,
                                      Instant fechaInicio, Instant fechaFin,
                                      Instant fechaCreacion, Instant fechaActualizacion) {
        return new Project(id, nombre, descripcion, teamId, responsableId, estado,
                fechaInicio, fechaFin, fechaCreacion, fechaActualizacion);
    }

    /**
     * Actualización parcial (PATCH): un argumento {@code null} significa "no cambiar ese campo".
     * Todo se valida antes de modificar el objeto, para no dejarlo a medias si algo falla.
     * El equipo (teamId) no se puede cambiar una vez creado el proyecto.
     */
    public void actualizar(String nuevoNombre, String nuevaDescripcion, ProjectStatus nuevoEstado,
                           Instant nuevaFechaInicio, Instant nuevaFechaFin, Long nuevoResponsableId) {
        String nombreFinal = nuevoNombre != null ? nuevoNombre : this.nombre;
        Instant inicioFinal = nuevaFechaInicio != null ? nuevaFechaInicio : this.fechaInicio;
        Instant finFinal = nuevaFechaFin != null ? nuevaFechaFin : this.fechaFin;

        validarNombre(nombreFinal);
        validarFechas(inicioFinal, finFinal);

        this.nombre = nombreFinal;
        if (nuevaDescripcion != null) {
            this.descripcion = nuevaDescripcion;
        }
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
        if (nuevoResponsableId != null) {
            this.responsableId = nuevoResponsableId;
        }
        this.fechaInicio = inicioFinal;
        this.fechaFin = finFinal;
        this.fechaActualizacion = Instant.now();
    }

    public void cambiarEstado(ProjectStatus nuevoEstado) {
        if (nuevoEstado == null) {
            throw new ProyectoInvalidoException("El estado del proyecto es obligatorio");
        }
        this.estado = nuevoEstado;
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
    public Long getResponsableId() { return responsableId; }
    public ProjectStatus getEstado() { return estado; }
    /** Derivado del estado: ya no se guarda aparte para que no pueda desincronizarse. */
    public boolean isActivo() { return estado == ProjectStatus.ACTIVO; }
    public Instant getFechaInicio() { return fechaInicio; }
    public Instant getFechaFin() { return fechaFin; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}
