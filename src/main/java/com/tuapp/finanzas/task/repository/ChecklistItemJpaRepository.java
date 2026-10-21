package com.tuapp.finanzas.task.repository;

import com.tuapp.finanzas.task.entity.ChecklistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChecklistItemJpaRepository extends JpaRepository<ChecklistItemEntity, Long> {
    List<ChecklistItemEntity> findByTareaIdOrderByPosicionAscIdAsc(Long tareaId);
    void deleteByTareaId(Long tareaId);
}
