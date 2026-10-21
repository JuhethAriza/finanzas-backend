package com.tuapp.finanzas.team.service;

import com.tuapp.finanzas.team.domain.exception.MiembroNoEncontradoException;
import com.tuapp.finanzas.team.domain.exception.MiembroYaEnEquipoException;
import com.tuapp.finanzas.team.domain.exception.TeamNoEncontradoException;
import com.tuapp.finanzas.team.domain.model.TeamMember;
import com.tuapp.finanzas.team.domain.port.in.TeamMemberUseCase;
import com.tuapp.finanzas.team.domain.port.out.TeamMemberRepositoryPort;
import com.tuapp.finanzas.team.domain.port.out.TeamRepositoryPort;
import com.tuapp.finanzas.user.domain.exception.UsuarioNoEncontradoException;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamMemberService implements TeamMemberUseCase {

    private final TeamMemberRepositoryPort memberRepository;
    private final TeamRepositoryPort teamRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public TeamMemberService(TeamMemberRepositoryPort memberRepository,
                             TeamRepositoryPort teamRepository,
                             UsuarioRepositoryPort usuarioRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public TeamMember agregarMiembro(Long teamId, Long userId, String rol) {
        teamRepository.buscarPorId(teamId)
                .orElseThrow(() -> new TeamNoEncontradoException(teamId));

        usuarioRepository.buscarPorId(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(userId));

        memberRepository.buscarPorTeamYUsuario(teamId, userId).ifPresent(m -> {
            throw new MiembroYaEnEquipoException();
        });

        TeamMember member = TeamMember.crear(teamId, userId, rol);
        return memberRepository.guardar(member);
    }

    @Override
    @Transactional
    public void eliminarMiembro(Long teamId, Long userId) {
        memberRepository.buscarPorTeamYUsuario(teamId, userId)
                .orElseThrow(MiembroNoEncontradoException::new);
        memberRepository.eliminar(teamId, userId);
    }

    @Override
    public List<TeamMember> listarMiembros(Long teamId) {
        teamRepository.buscarPorId(teamId)
                .orElseThrow(() -> new TeamNoEncontradoException(teamId));
        return memberRepository.listarPorTeam(teamId);
    }
}