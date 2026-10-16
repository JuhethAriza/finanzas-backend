package com.tuapp.finanzas.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "tarea_actividad")
@Getter
@Setter
@NoArgsConstructor
public class ActividadTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tarea_id", nullable = false)
    private Long tareaId;
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(nullable = false)
    private String accion;
    @Column(name = "recurso_id")
    private Long recursoId;
    @Column(columnDefinition = "TEXT")
    private String detalles;
    @Column(nullable = false, updatable = false)
    private Instant fecha;

    public ActividadTareaEntity(Long tareaId, Long usuarioId, String accion, Long recursoId, String detalles) {
        this.tareaId = tareaId;
        this.usuarioId = usuarioId;
        this.accion = accion;
        this.recursoId = recursoId;
        this.detalles = detalles;
        this.fecha = Instant.now();
    }
}
