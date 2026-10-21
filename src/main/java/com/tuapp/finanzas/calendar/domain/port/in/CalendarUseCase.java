package com.tuapp.finanzas.calendar.domain.port.in;

import com.tuapp.finanzas.calendar.domain.model.CalendarEvent;

import java.time.Instant;
import java.util.List;

public interface CalendarUseCase {
    List<CalendarEvent> obtenerEventos(Instant startDate, Instant endDate, Long projectId, Long userId);
}
