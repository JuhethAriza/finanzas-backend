package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.port.out.ProjectRepositoryPort;
import com.tuapp.finanzas.project.repository.ProjectJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectRepositoryJpaAdapter implements ProjectRepositoryPort {

    private final ProjectJpaRepository jpaRepository;
    private final ProjectEntityMapper mapper;

    public ProjectRepositoryJpaAdapter(ProjectJpaRepository jpaRepository, ProjectEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Project save(Project proyecto) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(proyecto)));
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Project> findByFiltros(Long teamId, Boolean activo, Pageable pageable) {
        return jpaRepository.buscarConFiltros(teamId, activo, pageable).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Project> findByRangoFechas(Instant start, Instant end, Long teamId) {
        return jpaRepository.buscarPorRangoFechas(start, end, teamId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}