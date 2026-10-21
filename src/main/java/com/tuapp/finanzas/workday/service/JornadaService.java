package com.tuapp.finanzas.workday.service;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import com.tuapp.finanzas.workday.domain.port.in.JornadaUseCase;
import com.tuapp.finanzas.workday.domain.port.out.JornadaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class JornadaService implements JornadaUseCase {
    private final JornadaRepositoryPort repositoryPort;

    public JornadaService(JornadaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Page<Jornada> listarPorTareaId(Long tareaId, Pageable pageable) {
        return repositoryPort.listarPorTareaId(tareaId, pageable);
    }

    @Override
    @Transactional
    public Jornada crear(Long tareaId, LocalDate fecha, Double horasTrabajadas, String notas) {
        Jornada jornada = Jornada.crear(tareaId, fecha, horasTrabajadas, notas);
        return repositoryPort.guardar(jornada);
    }

    @Override
    public Optional<Jornada> obtenerPorId(Long id) {
        return repositoryPort.buscarPorId(id);
    }

    @Override
    @Transactional
    public Jornada actualizar(Long id, LocalDate fecha, Double horasTrabajadas, String notas) {
        Jornada jornada = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jornada no encontrada con ID: " + id));
        jornada.actualizar(fecha, horasTrabajadas, notas);
        return repositoryPort.guardar(jornada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repositoryPort.eliminar(id);
    }
}
