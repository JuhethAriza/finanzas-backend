package com.tuapp.finanzas.team.adapter;

import com.tuapp.finanzas.team.domain.model.Team;
import com.tuapp.finanzas.team.domain.model.TeamMember;
import com.tuapp.finanzas.team.entity.TeamEntity;
import com.tuapp.finanzas.team.entity.TeamMemberEntity;
import org.springframework.stereotype.Component;

@Component
public class TeamEntityMapper {

    public TeamEntity toEntity(Team team) {
        return new TeamEntity(team.getId(), team.getNombre(), team.getDescripcion(), team.getFechaCreacion());
    }

    public Team toDomain(TeamEntity entity) {
        return Team.reconstruir(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getFechaCreacion());
    }

    public TeamMemberEntity toEntity(TeamMember member) {
        return new TeamMemberEntity(member.getId(), member.getTeamId(), member.getUserId(), member.getRol(), member.getFechaIngreso());
    }

    public TeamMember toDomain(TeamMemberEntity entity) {
        return TeamMember.reconstruir(entity.getId(), entity.getTeamId(), entity.getUserId(), entity.getRol(), entity.getFechaIngreso());
    }
}