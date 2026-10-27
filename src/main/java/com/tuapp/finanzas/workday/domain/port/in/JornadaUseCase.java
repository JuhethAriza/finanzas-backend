package com.tuapp.finanzas.workday.domain.port.in;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface JornadaUseCase {
    Page<Jornada> listarPorTareaId(Long tareaId, Pageable pageable);
    Jornada crear(Long tareaId, LocalDate fecha, Double horasTrabajadas, String notas);
    Optional<Jornada> obtenerPorId(Long id);
    Jornada actualizar(Long id, LocalDate fecha, Double horasTrabajadas, String notas);
    void eliminar(Long id);
}
