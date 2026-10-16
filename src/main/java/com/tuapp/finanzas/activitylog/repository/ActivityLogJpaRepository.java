package com.tuapp.finanzas.activitylog.repository;

import com.tuapp.finanzas.activitylog.entity.ActivityLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface ActivityLogJpaRepository extends JpaRepository<ActivityLogEntity, Long> {

    @Query("""
        SELECT a FROM ActivityLogEntity a
        WHERE (:action IS NULL OR a.action = :action)
        AND (:resourceType IS NULL OR a.resourceType = :resourceType)
        AND (:userId IS NULL OR a.userId = :userId)
        AND (:projectId IS NULL OR a.projectId = :projectId)
        AND (:desde IS NULL OR a.timestamp >= :desde)
        AND (:hasta IS NULL OR a.timestamp <= :hasta)
        ORDER BY a.timestamp DESC
    """)
    Page<ActivityLogEntity> buscarConFiltros(
            @Param("action") String action,
            @Param("resourceType") String resourceType,
            @Param("userId") Long userId,
            @Param("projectId") Long projectId,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta,
            Pageable pageable);
}
