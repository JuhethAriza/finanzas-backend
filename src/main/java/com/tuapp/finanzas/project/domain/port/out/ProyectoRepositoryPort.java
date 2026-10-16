package com.tuapp.finanzas.project.domain.port.out;

import com.tuapp.finanzas.project.domain.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProyectoRepositoryPort {
    Page<Proyecto> listar(Pageable pageable);
    Proyecto guardar(Proyecto proyecto);
    Optional<Proyecto> buscarPorId(Long id);
    void eliminar(Long id);
}
