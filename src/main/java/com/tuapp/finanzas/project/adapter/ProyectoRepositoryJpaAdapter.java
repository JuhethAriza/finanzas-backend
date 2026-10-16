package com.tuapp.finanzas.project.adapter;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import com.tuapp.finanzas.project.domain.port.out.ProyectoRepositoryPort;
import com.tuapp.finanzas.project.entity.ProyectoEntity;
import com.tuapp.finanzas.project.repository.ProyectoJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProyectoRepositoryJpaAdapter implements ProyectoRepositoryPort {
    private final ProyectoJpaRepository repository;
    private final ProyectoEntityMapper mapper;

    public ProyectoRepositoryJpaAdapter(ProyectoJpaRepository repository, ProyectoEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<Proyecto> listar(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Proyecto guardar(Proyecto proyecto) {
        ProyectoEntity entity = mapper.toEntity(proyecto);
        ProyectoEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Proyecto> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
