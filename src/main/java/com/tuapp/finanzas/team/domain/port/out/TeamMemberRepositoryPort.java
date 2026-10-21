package com.tuapp.finanzas.team.domain.port.out;

import com.tuapp.finanzas.team.domain.model.TeamMember;
import java.util.List;
import java.util.Optional;

public interface TeamMemberRepositoryPort {
    TeamMember guardar(TeamMember member);
    Optional<TeamMember> buscarPorTeamYUsuario(Long teamId, Long userId);
    List<TeamMember> listarPorTeam(Long teamId);
    void eliminar(Long teamId, Long userId);
    void eliminarTodosPorTeam(Long teamId);
}