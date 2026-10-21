package com.tuapp.finanzas.team.service;

import com.tuapp.finanzas.team.domain.model.Team;
import com.tuapp.finanzas.team.domain.exception.TeamNoEncontradoException;
import com.tuapp.finanzas.team.domain.port.out.TeamRepositoryPort;
import com.tuapp.finanzas.team.domain.port.in.TeamUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements TeamUseCase {

    private final TeamRepositoryPort repository;

    public TeamService(TeamRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Team crear(String nombre, String descripcion) {
        Team team = Team.crear(nombre, descripcion);
        return repository.guardar(team);
    }

    @Override
    public Team obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new TeamNoEncontradoException(id));
    }

    @Override
    public List<Team> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public Team actualizar(Long id, String nombre, String descripcion) {
        Team team = obtenerPorId(id);
        team.actualizar(nombre, descripcion);
        return repository.guardar(team);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id); // valida que exista antes de eliminar
        repository.eliminar(id);
    }
}