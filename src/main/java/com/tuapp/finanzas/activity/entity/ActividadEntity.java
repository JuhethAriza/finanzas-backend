package com.tuapp.finanzas.activity.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "actividades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActividadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Long proyectoId;

    @Column(nullable = false)
    @Builder.Default
    private int totalTasks = 0;

    @Column(nullable = false)
    @Builder.Default
    private int completedTasks = 0;

    @Column(nullable = false, updatable = false)
    private Instant fechaCreacion;

    private Instant fechaActualizacion;
}
