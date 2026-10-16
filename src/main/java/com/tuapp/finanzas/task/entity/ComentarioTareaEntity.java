package com.tuapp.finanzas.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "tarea_comentarios")
@Getter
@Setter
@NoArgsConstructor
public class ComentarioTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tarea_id", nullable = false)
    private Long tareaId;
    @Column(name = "autor_id", nullable = false)
    private Long autorId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    @Column(nullable = false, updatable = false)
    private Instant fechaCreacion;
    @Column(nullable = false)
    private Instant fechaActualizacion;

    public ComentarioTareaEntity(Long tareaId, Long autorId, String contenido) {
        this.tareaId = tareaId;
        this.autorId = autorId;
        this.contenido = contenido;
        this.fechaCreacion = Instant.now();
        this.fechaActualizacion = this.fechaCreacion;
    }
}
