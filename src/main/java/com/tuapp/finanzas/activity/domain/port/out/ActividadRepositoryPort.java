package com.tuapp.finanzas.activity.domain.port.out;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActividadRepositoryPort {
    Page<Actividad> listarPorProyectoId(Long proyectoId, Pageable pageable);
    Page<Actividad> listarPorUsuario(Long usuarioId, Pageable pageable);
    Actividad guardar(Actividad actividad);
    Optional<Actividad> buscarPorId(Long id);
    void eliminar(Long id);
}
