package com.tuapp.finanzas.team.domain.port.in;

import com.tuapp.finanzas.team.domain.model.TeamMember;
import java.util.List;

public interface TeamMemberUseCase {
    TeamMember agregarMiembro(Long teamId, Long userId, String rol);
    void eliminarMiembro(Long teamId, Long userId);
    List<TeamMember> listarMiembros(Long teamId);
}