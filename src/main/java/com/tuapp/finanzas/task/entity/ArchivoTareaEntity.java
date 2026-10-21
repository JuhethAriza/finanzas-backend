package com.tuapp.finanzas.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "tarea_archivos")
@Getter
@Setter
@NoArgsConstructor
public class ArchivoTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tarea_id", nullable = false)
    private Long tareaId;
    @Column(name = "subido_por", nullable = false)
    private Long subidoPor;
    @Column(nullable = false)
    private String nombre;
    @Column(name = "tipo_contenido", nullable = false)
    private String tipoContenido;
    @Column(nullable = false)
    private long tamano;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] contenido;
    @Column(nullable = false, updatable = false)
    private Instant fechaCreacion;

    public ArchivoTareaEntity(Long tareaId, Long subidoPor, String nombre, String tipoContenido, byte[] contenido) {
        this.tareaId = tareaId;
        this.subidoPor = subidoPor;
        this.nombre = nombre;
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
        this.tamano = contenido.length;
        this.fechaCreacion = Instant.now();
    }
}
