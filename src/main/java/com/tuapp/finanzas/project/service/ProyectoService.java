package com.tuapp.finanzas.project.service;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import com.tuapp.finanzas.project.domain.model.ProyectoUsuario;
import com.tuapp.finanzas.project.domain.model.enums.ProyectoEstado;
import com.tuapp.finanzas.project.domain.port.in.ProyectoUseCase;
import com.tuapp.finanzas.project.domain.port.out.ProyectoRepositoryPort;
import com.tuapp.finanzas.project.domain.port.out.ProyectoUsuarioRepositoryPort;
import com.tuapp.finanzas.team.domain.port.in.TeamMemberUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService implements ProyectoUseCase {
    private final ProyectoRepositoryPort repositoryPort;
    private final ProyectoUsuarioRepositoryPort usuarioRepositoryPort;
    private final TeamMemberUseCase teamMemberUseCase;

    public ProyectoService(ProyectoRepositoryPort repositoryPort, ProyectoUsuarioRepositoryPort usuarioRepositoryPort, TeamMemberUseCase teamMemberUseCase) {
        this.repositoryPort = repositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.teamMemberUseCase = teamMemberUseCase;
    }

    @Override
    public Page<Proyecto> listar(Pageable pageable) {
        return repositoryPort.listar(pageable);
    }

    @Override
    @Transactional
    public Proyecto crear(String nombre, String descripcion, Long equipoId, Long responsableId,
                          ProyectoEstado estado, LocalDate fechaInicio, LocalDate fechaFin) {
        Proyecto proyecto = Proyecto.crear(nombre, descripcion, equipoId, responsableId,
                                           estado, fechaInicio, fechaFin);
        return repositoryPort.guardar(proyecto);
    }

    @Override
    public Optional<Proyecto> obtenerPorId(Long id) {
        return repositoryPort.buscarPorId(id);
    }

    @Override
    @Transactional
    public Proyecto actualizar(Long id, String nombre, String descripcion, ProyectoEstado estado, LocalDate fechaInicio, LocalDate fechaFin, Long responsableId, Long equipoId) {
        Proyecto proyecto = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con ID: " + id));
        proyecto.actualizar(nombre, descripcion, estado, fechaInicio, fechaFin, responsableId, equipoId);
        return repositoryPort.guardar(proyecto);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repositoryPort.eliminar(id);
    }

    @Override
    @Transactional
    public void asignarUsuario(Long proyectoId, Long usuarioId) {
        Proyecto proyecto = repositoryPort.buscarPorId(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        // Validation: User must belong to the project's team
        Long equipoId = proyecto.getEquipoId();
        boolean esMiembroDelEquipo = teamMemberUseCase.listarMiembros(equipoId).stream()
                .anyMatch(m -> m.getUserId().equals(usuarioId));

        if (!esMiembroDelEquipo) {
            throw new IllegalArgumentException("El usuario no tiene autorización para pertenecer a este proyecto ya que no es miembro del equipo asignado");
        }

        if (usuarioRepositoryPort.existe(proyectoId, usuarioId)) {
            throw new IllegalArgumentException("El usuario ya está asignado a este proyecto");
        }

        ProyectoUsuario pu = ProyectoUsuario.crear(proyectoId, usuarioId);
        usuarioRepositoryPort.guardar(pu);
    }

    @Override
    @Transactional
    public void desasignarUsuario(Long proyectoId, Long usuarioId) {
        usuarioRepositoryPort.eliminar(proyectoId, usuarioId);
    }

    @Override
    public List<ProyectoUsuario> listarUsuarios(Long proyectoId) {
        return usuarioRepositoryPort.buscarPorProyectoId(proyectoId);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, ProyectoEstado nuevoEstado) {
        Proyecto proyecto = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        Proyecto actualizado = Proyecto.reconstruir(
                proyecto.getId(), proyecto.getNombre(), proyecto.getDescripcion(),
                nuevoEstado, proyecto.getFechaInicio(), proyecto.getFechaFin(),
                proyecto.getResponsableId(), proyecto.getEquipoId(),
                proyecto.getFechaCreacion(), proyecto.getFechaActualizacion()
        );
        repositoryPort.guardar(actualizado);
    }
}
