package com.tuapp.finanzas.team.adapter;

import com.tuapp.finanzas.team.domain.model.Team;
import com.tuapp.finanzas.team.domain.port.out.TeamRepositoryPort;
import com.tuapp.finanzas.team.repository.TeamJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamRepositoryJpaAdapter implements TeamRepositoryPort {

    private final TeamJpaRepository jpaRepository;
    private final TeamEntityMapper mapper;

    public TeamRepositoryJpaAdapter(TeamJpaRepository jpaRepository, TeamEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Team guardar(Team team) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(team)));
    }

    @Override
    public Optional<Team> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Team> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}