package com.tuapp.finanzas.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
// ¡Aquí está la corrección! Cambiamos .enum por .enums
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProyectoEstado estado;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Long responsableId;

    @Column(nullable = false)
    private Long equipoId;

    @Column(nullable = false, updatable = false)
    private Instant fechaCreacion;

    private Instant fechaActualizacion;
}