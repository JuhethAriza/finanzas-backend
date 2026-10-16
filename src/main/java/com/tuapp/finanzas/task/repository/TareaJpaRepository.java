package com.tuapp.finanzas.task.repository;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.entity.TareaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface TareaJpaRepository extends JpaRepository<TareaEntity, Long> {

    @Query("""
        SELECT t FROM TareaEntity t
        WHERE (:proyectoId IS NULL OR t.proyectoId = :proyectoId)
        AND (:actividadId IS NULL OR t.actividadId = :actividadId)
        AND (:asignadoA IS NULL OR t.asignadoA = :asignadoA)
        AND (:estado IS NULL OR t.estado = :estado)
        AND (:prioridad IS NULL OR t.prioridad = :prioridad)
        AND (:buscar IS NULL OR LOWER(t.titulo) LIKE LOWER(CONCAT('%', :buscar, '%'))
             OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :buscar, '%')))
        AND (:desde IS NULL OR t.fechaLimite >= :desde)
        AND (:hasta IS NULL OR t.fechaLimite <= :hasta)
        """)
    Page<TareaEntity> buscarConFiltros(@Param("proyectoId") Long proyectoId,
                                       @Param("actividadId") Long actividadId,
                                       @Param("asignadoA") Long asignadoA,
                                       @Param("estado") EstadoTarea estado,
                                       @Param("prioridad") PrioridadTarea prioridad,
                                       @Param("buscar") String buscar,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta") LocalDate hasta,
                                       Pageable pageable);

    long countByActividadIdAndEstado(Long actividadId, EstadoTarea estado);

    long countByActividadId(Long actividadId);
}