package com.tuapp.finanzas.project.domain.port.out;

import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {
    Project save(Project proyecto);
    Optional<Project> findById(Long id);
    Page<Project> findByFiltros(Long teamId, ProjectStatus estado, Pageable pageable);
    void deleteById(Long id);
    boolean existsById(Long id);
    /** Proyectos que se solapan con [start, end]; si projectId no es null, solo ese proyecto. */
    List<Project> findByRangoFechas(Instant start, Instant end, Long projectId);
}
