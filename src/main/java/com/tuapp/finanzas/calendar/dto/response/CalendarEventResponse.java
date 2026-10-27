package com.tuapp.finanzas.calendar.dto.response;

import java.time.Instant;

public record CalendarEventResponse(
        Long id,
        String title,
        String type,
        Instant start,
        Instant end,
        Long projectId
) {}