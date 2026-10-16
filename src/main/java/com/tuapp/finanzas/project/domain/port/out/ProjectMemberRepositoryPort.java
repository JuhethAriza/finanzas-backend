package com.tuapp.finanzas.project.domain.port.out;

import com.tuapp.finanzas.project.domain.port.in.ProjectUseCase.MiembroProyecto;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepositoryPort {
    MiembroProyecto agregar(Long proyectoId, Long usuarioId);
    List<MiembroProyecto> listarPorProyecto(Long proyectoId);
    Optional<MiembroProyecto> buscar(Long proyectoId, Long usuarioId);
    void eliminar(Long proyectoId, Long usuarioId);
}