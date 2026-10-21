package com.tuapp.finanzas.project.repository;

import com.tuapp.finanzas.project.domain.model.enums.ProjectStatus;
import com.tuapp.finanzas.project.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("""
        SELECT p FROM ProjectEntity p
        WHERE (:teamId IS NULL OR p.teamId = :teamId)
        AND (:estado IS NULL OR p.estado = :estado)
        """)
    Page<ProjectEntity> buscarConFiltros(@Param("teamId") Long teamId,
                                         @Param("estado") ProjectStatus estado,
                                         Pageable pageable);

    @Query("""
        SELECT p FROM ProjectEntity p
        WHERE p.fechaInicio <= :end AND p.fechaFin >= :start
        AND (:projectId IS NULL OR p.id = :projectId)
        """)
    List<ProjectEntity> buscarPorRangoFechas(@Param("start") Instant start,
                                             @Param("end") Instant end,
                                             @Param("projectId") Long projectId);
}
