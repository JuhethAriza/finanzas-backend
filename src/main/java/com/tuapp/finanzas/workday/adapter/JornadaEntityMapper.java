package com.tuapp.finanzas.workday.adapter;

import com.tuapp.finanzas.workday.domain.model.Jornada;
import com.tuapp.finanzas.workday.entity.JornadaEntity;
import org.springframework.stereotype.Component;

@Component
public class JornadaEntityMapper {
    public Jornada toDomain(JornadaEntity entity) {
        return Jornada.reconstruir(
                entity.getId(),
                entity.getTareaId(),
                entity.getFecha(),
                entity.getHorasTrabajadas(),
                entity.getNotas(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public JornadaEntity toEntity(Jornada domain) {
        return JornadaEntity.builder()
                .id(domain.getId())
                .tareaId(domain.getTareaId())
                .fecha(domain.getFecha())
                .horasTrabajadas(domain.getHorasTrabajadas())
                .notas(domain.getNotas())
                .fechaCreacion(domain.getFechaCreacion())
                .fechaActualizacion(domain.getFechaActualizacion())
                .build();
    }
}
