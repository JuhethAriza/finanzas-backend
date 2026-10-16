package com.tuapp.finanzas.activitylog.service;

import com.tuapp.finanzas.activitylog.dto.response.ActivityLogResponse;
import com.tuapp.finanzas.activitylog.entity.ActivityLogEntity;
import com.tuapp.finanzas.activitylog.repository.ActivityLogJpaRepository;
import com.tuapp.finanzas.user.adapter.UsuarioRepositoryJpaAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityLogService {

    private final ActivityLogJpaRepository logRepository;
    private final UsuarioRepositoryJpaAdapter usuarioRepository;

    public ActivityLogService(ActivityLogJpaRepository logRepository,
                              UsuarioRepositoryJpaAdapter usuarioRepository) {
        this.logRepository = logRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<ActivityLogResponse> listar(String action, String resourceType, Long userId,
                                            Long projectId, Instant desde, Instant hasta,
                                            Pageable pageable) {
        Page<ActivityLogEntity> pagina = logRepository.buscarConFiltros(
                action, resourceType, userId, projectId, desde, hasta, pageable);

        // Batch lookup de nombres de usuario
        Set<Long> userIds = pagina.getContent().stream()
                .map(ActivityLogEntity::getUserId)
                .filter(id -> id != null)
                .collect(Collectors.toSet());

        Map<Long, String> userNames = userIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> usuarioRepository.buscarPorId(id)
                                .map(u -> u.getNombre())
                                .orElse("Desconocido")
                ));

        return pagina.map(log -> toResponse(log, userNames));
    }

    public void registrar(Long userId, String action, String resourceType, Long resourceId,
                          String resourceName, Long projectId, String projectName, String detail) {
        ActivityLogEntity entity = ActivityLogEntity.builder()
                .userId(userId)
                .action(action)
                .resourceType(resourceType)
                .resourceId(resourceId)
                .resourceName(resourceName)
                .projectId(projectId)
                .projectName(projectName)
                .detail(detail)
                .timestamp(Instant.now())
                .build();
        logRepository.save(entity);
    }

    private ActivityLogResponse toResponse(ActivityLogEntity entity, Map<Long, String> userNames) {
        String userName = entity.getUserId() != null
                ? userNames.getOrDefault(entity.getUserId(), "Desconocido")
                : null;

        return new ActivityLogResponse(
                entity.getId(),
                entity.getUserId(),
                userName,
                entity.getAction(),
                entity.getResourceType(),
                entity.getResourceId(),
                entity.getResourceName(),
                entity.getProjectName(),
                entity.getTimestamp(),
                entity.getDetail()
        );
    }
}
