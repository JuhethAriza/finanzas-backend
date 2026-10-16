package com.tuapp.finanzas.task.repository;

import com.tuapp.finanzas.task.entity.ActividadTareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActividadTareaJpaRepository extends JpaRepository<ActividadTareaEntity, Long> {
    List<ActividadTareaEntity> findByTareaIdOrderByFechaDesc(Long tareaId);
    void deleteByTareaId(Long tareaId);
}
