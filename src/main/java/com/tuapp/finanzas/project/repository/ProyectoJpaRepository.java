package com.tuapp.finanzas.project.repository;

import com.tuapp.finanzas.project.entity.ProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoJpaRepository extends JpaRepository<ProyectoEntity, Long> {
}
