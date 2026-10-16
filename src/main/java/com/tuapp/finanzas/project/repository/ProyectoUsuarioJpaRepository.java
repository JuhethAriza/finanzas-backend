package com.tuapp.finanzas.project.repository;

import com.tuapp.finanzas.project.entity.ProyectoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoUsuarioJpaRepository extends JpaRepository<ProyectoUsuarioEntity, Long> {
    boolean existsByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);
    void deleteByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);
    List<ProyectoUsuarioEntity> findByProyectoId(Long proyectoId);
}
