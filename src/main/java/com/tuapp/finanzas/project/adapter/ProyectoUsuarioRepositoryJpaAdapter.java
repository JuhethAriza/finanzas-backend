package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.model.ProyectoUsuario;
import com.tuapp.finanzas.project.domain.port.out.ProyectoUsuarioRepositoryPort;
import com.tuapp.finanzas.project.entity.ProyectoUsuarioEntity;
import com.tuapp.finanzas.project.repository.ProyectoUsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProyectoUsuarioRepositoryJpaAdapter implements ProyectoUsuarioRepositoryPort {
    private final ProyectoUsuarioJpaRepository repository;

    public ProyectoUsuarioRepositoryJpaAdapter(ProyectoUsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProyectoUsuario guardar(ProyectoUsuario proyectoUsuario) {
        ProyectoUsuarioEntity entity = ProyectoUsuarioEntity.builder()
                .proyectoId(proyectoUsuario.getProyectoId())
                .usuarioId(proyectoUsuario.getUsuarioId())
                .fechaAsignacion(proyectoUsuario.getFechaAsignacion())
                .build();
        ProyectoUsuarioEntity saved = repository.save(entity);
        return ProyectoUsuario.reconstruir(saved.getId(), saved.getProyectoId(), saved.getUsuarioId(), saved.getFechaAsignacion());
    }

    @Override
    public void eliminar(Long proyectoId, Long usuarioId) {
        repository.deleteByProyectoIdAndUsuarioId(proyectoId, usuarioId);
    }

    @Override
    public boolean existe(Long proyectoId, Long usuarioId) {
        return repository.existsByProyectoIdAndUsuarioId(proyectoId, usuarioId);
    }

    @Override
    public List<ProyectoUsuario> buscarPorProyectoId(Long proyectoId) {
        return repository.findByProyectoId(proyectoId).stream()
                .map(e -> ProyectoUsuario.reconstruir(e.getId(), e.getProyectoId(), e.getUsuarioId(), e.getFechaAsignacion()))
                .collect(Collectors.toList());
    }
}
