package com.tuapp.finanzas.workday.repository;

import com.tuapp.finanzas.workday.entity.JornadaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JornadaJpaRepository extends JpaRepository<JornadaEntity, Long> {
    Page<JornadaEntity> findByTareaId(Long tareaId, Pageable pageable);
}
