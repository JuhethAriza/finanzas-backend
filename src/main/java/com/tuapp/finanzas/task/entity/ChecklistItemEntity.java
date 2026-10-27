package com.tuapp.finanzas.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarea_checklist")
@Getter
@Setter
@NoArgsConstructor
public class ChecklistItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tarea_id", nullable = false)
    private Long tareaId;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private boolean completado;

    @Column(nullable = false)
    private int posicion;

    public ChecklistItemEntity(Long tareaId, String contenido, boolean completado, int posicion) {
        this.tareaId = tareaId;
        this.contenido = contenido;
        this.completado = completado;
        this.posicion = posicion;
    }
}
