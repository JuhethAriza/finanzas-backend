package com.tuapp.finanzas.task.adapter;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.task.domain.port.out.TareaRepositoryPort;
import com.tuapp.finanzas.task.repository.TareaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Component
public class TareaRepositoryJpaAdapter implements TareaRepositoryPort {

    private final TareaJpaRepository jpaRepository;
    private final TareaEntityMapper mapper;

    public TareaRepositoryJpaAdapter(TareaJpaRepository jpaRepository, TareaEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Tarea save(Tarea tarea) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(tarea)));
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Tarea> findByFiltros(Long proyectoId, Long actividadId, Long asignadoA, EstadoTarea estado,
                                     PrioridadTarea prioridad, String buscar, LocalDate desde, LocalDate hasta,
                                     Pageable pageable) {
        return jpaRepository.buscarConFiltros(proyectoId, actividadId, asignadoA, estado, prioridad,
                        buscar, desde, hasta, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long countByActividadId(Long actividadId) {
        return jpaRepository.countByActividadId(actividadId);
    }

    @Override
    public long countByActividadIdAndEstado(Long actividadId, EstadoTarea estado) {
        return jpaRepository.countByActividadIdAndEstado(actividadId, estado);
    }

    @Override
    public long countByProyectoId(Long proyectoId) {
        return jpaRepository.countByProyectoId(proyectoId);
    }

    @Override
    public long countByProyectoIdAndEstado(Long proyectoId, EstadoTarea estado) {
        return jpaRepository.countByProyectoIdAndEstado(proyectoId, estado);
    }

    @Override
    public List<Tarea> findByRangoFechas(Instant start, Instant end, Long proyectoId, Long usuarioId) {
        return jpaRepository.buscarPorRangoFechas(start, end, proyectoId, usuarioId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}