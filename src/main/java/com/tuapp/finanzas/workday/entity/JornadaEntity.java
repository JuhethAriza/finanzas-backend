package com.tuapp.finanzas.workday.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "jornadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JornadaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tareaId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double horasTrabajadas;

    private String notas;

    @Column(nullable = false, updatable = false)
    private Instant fechaCreacion;

    private Instant fechaActualizacion;
}
