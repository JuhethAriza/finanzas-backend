package com.tuapp.finanzas.team.domain.port.in;

import com.tuapp.finanzas.team.domain.model.Team;
import java.util.List;

public interface TeamUseCase {
    Team crear(String nombre, String descripcion);
    Team obtenerPorId(Long id);
    List<Team> listarTodos();
    Team actualizar(Long id, String nombre, String descripcion);
    void eliminar(Long id);
}