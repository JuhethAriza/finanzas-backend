package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.entity.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectEntityMapper {

    public ProjectEntity toEntity(Project proyecto) {
        return new ProjectEntity(
                proyecto.getId(), proyecto.getNombre(), proyecto.getDescripcion(),
                proyecto.getTeamId(), proyecto.isActivo(),
                proyecto.getFechaInicio(), proyecto.getFechaFin(),
                proyecto.getFechaCreacion(), proyecto.getFechaActualizacion()
        );
    }

    public Project toDomain(ProjectEntity entity) {
        return Project.reconstruir(
                entity.getId(), entity.getNombre(), entity.getDescripcion(),
                entity.getTeamId(), entity.isActivo(),
                entity.getFechaInicio(), entity.getFechaFin(),
                entity.getFechaCreacion(), entity.getFechaActualizacion()
        );
    }
}