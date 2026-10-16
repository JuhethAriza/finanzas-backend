package com.tuapp.finanzas.task.adapter;

import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.task.entity.TareaEntity;
import org.springframework.stereotype.Component;

@Component
public class TareaEntityMapper {

    public TareaEntity toEntity(Tarea tarea) {
        return new TareaEntity(
            tarea.getId(), tarea.getTitulo(), tarea.getDescripcion(), tarea.getActividadId(), tarea.getProyectoId(),
            tarea.getAsignadoA(), tarea.getEstado(), tarea.getPrioridad(), tarea.getFechaLimite(), tarea.getEtiquetas(),
                tarea.getFechaCreacion(), tarea.getFechaActualizacion()
        );
    }

    public Tarea toDomain(TareaEntity entity) {
        return Tarea.reconstruir(
                entity.getId(), entity.getTitulo(), entity.getDescripcion(), entity.getActividadId(), entity.getProyectoId(),
                entity.getAsignadoA(), entity.getEstado(), entity.getPrioridad(), entity.getFechaLimite(), entity.getEtiquetas(),
                entity.getFechaCreacion(), entity.getFechaActualizacion()
        );
    }
}