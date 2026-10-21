package com.tuapp.finanzas.team.domain.port.out;

import com.tuapp.finanzas.team.domain.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamRepositoryPort {
    Team guardar(Team team);
    Optional<Team> buscarPorId(Long id);
    List<Team> listarTodos();
    void eliminar(Long id);
}