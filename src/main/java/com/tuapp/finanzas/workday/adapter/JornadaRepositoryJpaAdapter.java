package com.tuapp.finanzas.workday.adapter;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import com.tuapp.finanzas.workday.domain.port.out.JornadaRepositoryPort;
import com.tuapp.finanzas.workday.entity.JornadaEntity;
import com.tuapp.finanzas.workday.repository.JornadaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JornadaRepositoryJpaAdapter implements JornadaRepositoryPort {
    private final JornadaJpaRepository repository;
    private final JornadaEntityMapper mapper;

    public JornadaRepositoryJpaAdapter(JornadaJpaRepository repository, JornadaEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<Jornada> listarPorTareaId(Long tareaId, Pageable pageable) {
        return repository.findByTareaId(tareaId, pageable).map(mapper::toDomain);
    }

    @Override
    public Jornada guardar(Jornada jornada) {
        JornadaEntity entity = mapper.toEntity(jornada);
        JornadaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Jornada> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
