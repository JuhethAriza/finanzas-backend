package com.tuapp.finanzas.activitylog.controller;

import com.tuapp.finanzas.activitylog.dto.response.ActivityLogResponse;
import com.tuapp.finanzas.activitylog.service.ActivityLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ResponseEntity<Page<ActivityLogResponse>> listar(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Pageable pageable) {

        Instant desdeInstant = desde != null ? desde.atStartOfDay().toInstant(ZoneOffset.UTC) : null;
        Instant hastaInstant = hasta != null ? hasta.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC) : null;

        Page<ActivityLogResponse> pagina = activityLogService.listar(
                action, resourceType, userId, projectId, desdeInstant, hastaInstant, pageable);

        return ResponseEntity.ok(pagina);
    }
}
