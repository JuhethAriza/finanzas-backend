package com.tuapp.finanzas.task.repository;

import com.tuapp.finanzas.task.entity.ComentarioTareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioTareaJpaRepository extends JpaRepository<ComentarioTareaEntity, Long> {
    List<ComentarioTareaEntity> findByTareaIdOrderByFechaCreacionAsc(Long tareaId);
    void deleteByTareaId(Long tareaId);
}
