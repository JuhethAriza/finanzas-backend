package com.tuapp.finanzas.activity.adapter;

import com.tuapp.finanzas.activity.domain.model.Actividad;
import com.tuapp.finanzas.activity.domain.port.out.ActividadRepositoryPort;
import com.tuapp.finanzas.activity.entity.ActividadEntity;
import com.tuapp.finanzas.activity.repository.ActividadJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActividadRepositoryJpaAdapter implements ActividadRepositoryPort {
    private final ActividadJpaRepository repository;
    private final ActividadEntityMapper mapper;

    public ActividadRepositoryJpaAdapter(ActividadJpaRepository repository, ActividadEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<Actividad> listarPorProyectoId(Long proyectoId, Pageable pageable) {
        return repository.findByProyectoId(proyectoId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Actividad> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return repository.findByUsuarioId(usuarioId, pageable).map(mapper::toDomain);
    }

    @Override
    public Actividad guardar(Actividad actividad) {
        ActividadEntity entity = mapper.toEntity(actividad);
        ActividadEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Actividad> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
