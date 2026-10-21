package com.tuapp.finanzas.workday.domain.port.out;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JornadaRepositoryPort {
    Page<Jornada> listarPorTareaId(Long tareaId, Pageable pageable);
    Jornada guardar(Jornada jornada);
    Optional<Jornada> buscarPorId(Long id);
    void eliminar(Long id);
}
