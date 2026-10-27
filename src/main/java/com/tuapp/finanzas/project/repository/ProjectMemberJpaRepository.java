package com.tuapp.finanzas.project.repository;

import com.tuapp.finanzas.project.entity.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMemberEntity, Long> {
    List<ProjectMemberEntity> findByProyectoId(Long proyectoId);
    Optional<ProjectMemberEntity> findByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);
    void deleteByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);
}