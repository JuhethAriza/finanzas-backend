package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.port.in.ProjectUseCase.MiembroProyecto;
import com.tuapp.finanzas.project.domain.port.out.ProjectMemberRepositoryPort;
import com.tuapp.finanzas.project.entity.ProjectMemberEntity;
import com.tuapp.finanzas.project.repository.ProjectMemberJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectMemberRepositoryJpaAdapter implements ProjectMemberRepositoryPort {

    private final ProjectMemberJpaRepository jpaRepository;

    public ProjectMemberRepositoryJpaAdapter(ProjectMemberJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MiembroProyecto agregar(Long proyectoId, Long usuarioId) {
        ProjectMemberEntity entity = jpaRepository.save(
                new ProjectMemberEntity(proyectoId, usuarioId, Instant.now()));
        return toDomain(entity);
    }

    @Override
    public List<MiembroProyecto> listarPorProyecto(Long proyectoId) {
        return jpaRepository.findByProyectoId(proyectoId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<MiembroProyecto> buscar(Long proyectoId, Long usuarioId) {
        return jpaRepository.findByProyectoIdAndUsuarioId(proyectoId, usuarioId).map(this::toDomain);
    }

    @Override
    public void eliminar(Long proyectoId, Long usuarioId) {
        jpaRepository.deleteByProyectoIdAndUsuarioId(proyectoId, usuarioId);
    }

    private MiembroProyecto toDomain(ProjectMemberEntity entity) {
        return new MiembroProyecto(entity.getUsuarioId(), entity.getFechaIngreso());
    }
}