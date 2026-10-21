package com.tuapp.finanzas.team.adapter;

import com.tuapp.finanzas.team.domain.model.TeamMember;
import com.tuapp.finanzas.team.domain.port.out.TeamMemberRepositoryPort;
import com.tuapp.finanzas.team.repository.TeamMemberJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamMemberRepositoryJpaAdapter implements TeamMemberRepositoryPort {

    private final TeamMemberJpaRepository jpaRepository;
    private final TeamEntityMapper mapper;

    public TeamMemberRepositoryJpaAdapter(TeamMemberJpaRepository jpaRepository, TeamEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TeamMember guardar(TeamMember member) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(member)));
    }

    @Override
    public Optional<TeamMember> buscarPorTeamYUsuario(Long teamId, Long userId) {
        return jpaRepository.findByTeamIdAndUserId(teamId, userId).map(mapper::toDomain);
    }

    @Override
    public List<TeamMember> listarPorTeam(Long teamId) {
        return jpaRepository.findByTeamId(teamId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void eliminar(Long teamId, Long userId) {
        jpaRepository.deleteByTeamIdAndUserId(teamId, userId);
    }

    @Override
    public void eliminarTodosPorTeam(Long teamId) {
        jpaRepository.deleteByTeamId(teamId);
    }
}