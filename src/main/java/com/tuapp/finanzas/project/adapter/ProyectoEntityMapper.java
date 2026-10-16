package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import com.tuapp.finanzas.project.entity.ProyectoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProyectoEntityMapper {
    public Proyecto toDomain(ProyectoEntity entity) {
        return Proyecto.reconstruir(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getEstado(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getResponsableId(),
                entity.getEquipoId(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public ProyectoEntity toEntity(Proyecto domain) {
        return ProyectoEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .descripcion(domain.getDescripcion())
                .estado(domain.getEstado())
                .fechaInicio(domain.getFechaInicio())
                .fechaFin(domain.getFechaFin())
                .responsableId(domain.getResponsableId())
                .equipoId(domain.getEquipoId())
                .fechaCreacion(domain.getFechaCreacion())
                .fechaActualizacion(domain.getFechaActualizacion())
                .build();
    }
}
