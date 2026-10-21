package com.tuapp.finanzas.calendar.domain.model;

import java.time.Instant;

public class CalendarEvent {

    private final Long id;
    private final String title;
    private final TipoEvento type;
    private final Instant start;
    private final Instant end;
    private final Long projectId;

    public CalendarEvent(Long id, String title, TipoEvento type, Instant start, Instant end, Long projectId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.projectId = projectId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public TipoEvento getType() { return type; }
    public Instant getStart() { return start; }
    public Instant getEnd() { return end; }
    public Long getProjectId() { return projectId; }

    public enum TipoEvento {
        PROJECT, TASK
    }
}