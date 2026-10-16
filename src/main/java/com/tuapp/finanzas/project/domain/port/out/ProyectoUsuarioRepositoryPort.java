package com.tuapp.finanzas.project.domain.port.out;

import com.tuapp.finanzas.project.domain.model.ProyectoUsuario;
import java.util.List;
import java.util.Optional;

public interface ProyectoUsuarioRepositoryPort {
    ProyectoUsuario guardar(ProyectoUsuario proyectoUsuario);
    void eliminar(Long proyectoId, Long usuarioId);
    boolean existe(Long proyectoId, Long usuarioId);
    List<ProyectoUsuario> buscarPorProyectoId(Long proyectoId);
}
