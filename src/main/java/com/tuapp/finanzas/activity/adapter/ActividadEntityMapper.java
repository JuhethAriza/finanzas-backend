package com.tuapp.finanzas.activity.adapter;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import com.tuapp.finanzas.activity.entity.ActividadEntity;
import org.springframework.stereotype.Component;

@Component
public class ActividadEntityMapper {
    public Actividad toDomain(ActividadEntity entity) {
        return Actividad.reconstruir(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getProyectoId(),
                entity.getTotalTasks(),
                entity.getCompletedTasks(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public ActividadEntity toEntity(Actividad domain) {
        return ActividadEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .descripcion(domain.getDescripcion())
                .proyectoId(domain.getProyectoId())
                .totalTasks(domain.getTotalTasks())
                .completedTasks(domain.getCompletedTasks())
                .fechaCreacion(domain.getFechaCreacion())
                .fechaActualizacion(domain.getFechaActualizacion())
                .build();
    }
}
