package com.tuapp.finanzas.task.domain.port.out;

import com.tuapp.finanzas.task.domain.model.EstadoTarea;
import com.tuapp.finanzas.task.domain.model.PrioridadTarea;
import com.tuapp.finanzas.task.domain.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.time.LocalDate;

public interface TareaRepositoryPort {
    Tarea save(Tarea tarea);
    Optional<Tarea> findById(Long id);
    Page<Tarea> findByFiltros(Long proyectoId, Long actividadId, Long asignadoA, EstadoTarea estado,
                              PrioridadTarea prioridad, String buscar, LocalDate desde, LocalDate hasta, Pageable pageable);
    void deleteById(Long id);
    boolean existsById(Long id);
    long countByActividadId(Long actividadId);
    long countByActividadIdAndEstado(Long actividadId, EstadoTarea estado);
}