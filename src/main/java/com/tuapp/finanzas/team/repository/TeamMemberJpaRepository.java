package com.tuapp.finanzas.team.repository;

import com.tuapp.finanzas.team.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberJpaRepository extends JpaRepository<TeamMemberEntity, Long> {

    Optional<TeamMemberEntity> findByTeamIdAndUserId(Long teamId, Long userId);

    List<TeamMemberEntity> findByTeamId(Long teamId);

    void deleteByTeamIdAndUserId(Long teamId, Long userId);

    void deleteByTeamId(Long teamId);
}