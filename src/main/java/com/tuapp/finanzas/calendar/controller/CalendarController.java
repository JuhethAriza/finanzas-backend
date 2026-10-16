package com.tuapp.finanzas.calendar.controller;

import com.tuapp.finanzas.calendar.domain.model.CalendarEvent;
import com.tuapp.finanzas.calendar.domain.port.in.CalendarUseCase;
import com.tuapp.finanzas.calendar.dto.response.CalendarEventResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarUseCase calendarUseCase;

    public CalendarController(CalendarUseCase calendarUseCase) {
        this.calendarUseCase = calendarUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CalendarEventResponse>> obtenerEventos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long userId) {

        List<CalendarEventResponse> eventos = calendarUseCase.obtenerEventos(startDate, endDate, projectId, userId)
                .stream()
                .map(e -> new CalendarEventResponse(
                        e.getId(), e.getTitle(), e.getType().name().toLowerCase(),
                        e.getStart(), e.getEnd(), e.getProjectId()))
                .toList();

        return ResponseEntity.ok(eventos);
    }
}