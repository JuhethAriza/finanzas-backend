package com.tuapp.finanzas.activity.repository;

import com.tuapp.finanzas.activity.entity.ActividadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActividadJpaRepository extends JpaRepository<ActividadEntity, Long> {
    Page<ActividadEntity> findByProyectoId(Long proyectoId, Pageable pageable);

    @Query("SELECT a FROM ActividadEntity a JOIN ProjectMemberEntity pm ON a.proyectoId = pm.proyectoId WHERE pm.usuarioId = :usuarioId")
    Page<ActividadEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
