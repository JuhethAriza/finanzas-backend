package com.tuapp.finanzas.team.repository;

import com.tuapp.finanzas.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {
}