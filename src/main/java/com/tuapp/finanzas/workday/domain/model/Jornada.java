package com.tuapp.finanzas.workday.domain.model;

import java.time.Instant;
import java.time.LocalDate;

public class Jornada {
    private final Long id;
    private final Long tareaId;
    private LocalDate fecha;
    private Double horasTrabajadas;
    private String notas;
    private final Instant fechaCreacion;
    private Instant fechaActualizacion;

    private Jornada(Long id, Long tareaId, LocalDate fecha, Double horasTrabajadas, String notas, Instant fechaCreacion, Instant fechaActualizacion) {
        this.id = id;
        this.tareaId = tareaId;
        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
        this.notas = notas;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Jornada crear(Long tareaId, LocalDate fecha, Double horasTrabajadas, String notas) {
        if (tareaId == null) {
            throw new IllegalArgumentException("La jornada debe estar asociada a una tarea");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la jornada es obligatoria");
        }
        if (horasTrabajadas == null || horasTrabajadas <= 0) {
            throw new IllegalArgumentException("Las horas trabajadas deben ser mayores a cero");
        }
        Instant ahora = Instant.now();
        return new Jornada(null, tareaId, fecha, horasTrabajadas, notas, ahora, ahora);
    }

    public static Jornada reconstruir(Long id, Long tareaId, LocalDate fecha, Double horasTrabajadas, String notas, Instant fechaCreacion, Instant fechaActualizacion) {
        return new Jornada(id, tareaId, fecha, horasTrabajadas, notas, fechaCreacion, fechaActualizacion);
    }

    public void actualizar(LocalDate nuevaFecha, Double nuevasHoras, String nuevasNotas) {
        if (nuevaFecha == null) throw new IllegalArgumentException("La fecha es obligatoria");
        if (nuevasHoras == null || nuevasHoras <= 0) throw new IllegalArgumentException("Las horas deben ser mayores a cero");

        this.fecha = nuevaFecha;
        this.horasTrabajadas = nuevasHoras;
        this.notas = nuevasNotas;
        this.fechaActualizacion = java.time.Instant.now();
    }

    public Long getId() { return id; }
    public Long getTareaId() { return tareaId; }
    public LocalDate getFecha() { return fecha; }
    public Double getHorasTrabajadas() { return horasTrabajadas; }
    public String getNotas() { return notas; }
    public Instant getFechaCreacion() { return fechaCreacion; }
    public Instant getFechaActualizacion() { return fechaActualizacion; }
}
