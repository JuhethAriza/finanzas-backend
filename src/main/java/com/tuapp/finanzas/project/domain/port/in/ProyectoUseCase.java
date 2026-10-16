package com.tuapp.finanzas.project.domain.port.in;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import com.tuapp.finanzas.project.domain.model.ProyectoUsuario;
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;

public interface ProyectoUseCase {
    Page<Proyecto> listar(Pageable pageable);
    Proyecto crear(String nombre, String descripcion, Long equipoId, Long responsableId,
                   ProyectoEstado estado, java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin);
    Optional<Proyecto> obtenerPorId(Long id);
    Proyecto actualizar(Long id, String nombre, String descripcion, ProyectoEstado estado, java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin, Long responsableId, Long equipoId);
    void eliminar(Long id);

    // User management
    void asignarUsuario(Long proyectoId, Long usuarioId);
    void desasignarUsuario(Long proyectoId, Long usuarioId);
    java.util.List<ProyectoUsuario> listarUsuarios(Long proyectoId);

    // Status management
    void cambiarEstado(Long id, ProyectoEstado nuevoEstado);
}
