package com.tuapp.finanzas.task.repository;

import com.tuapp.finanzas.task.entity.ArchivoTareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArchivoTareaJpaRepository extends JpaRepository<ArchivoTareaEntity, Long> {
    List<ArchivoTareaEntity> findByTareaIdOrderByFechaCreacionDesc(Long tareaId);
    void deleteByTareaId(Long tareaId);
}
