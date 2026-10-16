package com.tuapp.finanzas.activitylog.dto.response;

import java.time.Instant;

public record ActivityLogResponse(
        Long id,
        Long userId,
        String userName,
        String action,
        String resourceType,
        Long resourceId,
        String resourceName,
        String projectName,
        Instant timestamp,
        String detail
) {}
