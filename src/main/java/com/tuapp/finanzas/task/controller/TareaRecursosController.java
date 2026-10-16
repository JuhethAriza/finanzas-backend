package com.tuapp.finanzas.task.controller;

import com.tuapp.finanzas.activitylog.service.ActivityLogService;
import com.tuapp.finanzas.task.entity.*;
import com.tuapp.finanzas.task.repository.*;
import com.tuapp.finanzas.task.repository.TareaJpaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks/{taskId}")
public class TareaRecursosController {

    private final TareaJpaRepository tareas;
    private final ChecklistItemJpaRepository checklist;
    private final ComentarioTareaJpaRepository comentarios;
    private final ArchivoTareaJpaRepository archivos;
    private final ActividadTareaJpaRepository actividad;
    private final ActivityLogService activityLogService;

    public TareaRecursosController(TareaJpaRepository tareas, ChecklistItemJpaRepository checklist,
                                   ComentarioTareaJpaRepository comentarios, ArchivoTareaJpaRepository archivos,
                                   ActividadTareaJpaRepository actividad, ActivityLogService activityLogService) {
        this.tareas = tareas;
        this.checklist = checklist;
        this.comentarios = comentarios;
        this.archivos = archivos;
        this.actividad = actividad;
        this.activityLogService = activityLogService;
    }

    @GetMapping("/checklist")
    public List<ChecklistItemEntity> listarChecklist(@PathVariable Long taskId) {
        requireTask(taskId);
        return checklist.findByTareaIdOrderByPosicionAscIdAsc(taskId);
    }

    @PostMapping("/checklist")
    @Transactional
    public ChecklistItemEntity crearChecklist(@PathVariable Long taskId, @Valid @RequestBody ChecklistRequest request,
                                              Authentication authentication) {
        requireTask(taskId);
        ChecklistItemEntity item = checklist.save(new ChecklistItemEntity(taskId, request.contenido(), false, request.posicion()));
        logGlobal(taskId, actor(authentication), "CHECKLIST_CREADO", "Task", item.getId(), item.getContenido());
        return item;
    }

    @PatchMapping("/checklist/{itemId}")
    @Transactional
    public ChecklistItemEntity actualizarChecklist(@PathVariable Long taskId, @PathVariable Long itemId,
                                                   @Valid @RequestBody ChecklistUpdateRequest request,
                                                   Authentication authentication) {
        ChecklistItemEntity item = checklist.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Elemento de checklist no encontrado"));
        if (!taskId.equals(item.getTareaId())) throw new IllegalArgumentException("El elemento no pertenece a la tarea");
        if (request.contenido() != null) item.setContenido(request.contenido());
        if (request.completado() != null) item.setCompletado(request.completado());
        if (request.posicion() != null) item.setPosicion(request.posicion());
        ChecklistItemEntity saved = checklist.save(item);
        logGlobal(taskId, actor(authentication), "CHECKLIST_ACTUALIZADO", "Task", itemId, item.getContenido());
        return saved;
    }

    @DeleteMapping("/checklist/{itemId}")
    @Transactional
    public ResponseEntity<Void> eliminarChecklist(@PathVariable Long taskId, @PathVariable Long itemId,
                                                  Authentication authentication) {
        ChecklistItemEntity item = checklist.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Elemento de checklist no encontrado"));
        if (!taskId.equals(item.getTareaId())) throw new IllegalArgumentException("El elemento no pertenece a la tarea");
        checklist.delete(item);
        logGlobal(taskId, actor(authentication), "CHECKLIST_ELIMINADO", "Task", itemId, item.getContenido());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments")
    public List<ComentarioResponse> listarComentarios(@PathVariable Long taskId) {
        requireTask(taskId);
        return comentarios.findByTareaIdOrderByFechaCreacionAsc(taskId).stream().map(ComentarioResponse::from).toList();
    }

    @PostMapping("/comments")
    @Transactional
    public ComentarioResponse crearComentario(@PathVariable Long taskId, @Valid @RequestBody ComentarioRequest request,
                                              Authentication authentication) {
        requireTask(taskId);
        Long userId = actor(authentication);
        ComentarioTareaEntity comentario = comentarios.save(new ComentarioTareaEntity(taskId, userId, request.contenido()));
        logGlobal(taskId, userId, "COMENTARIO_CREADO", "Comment", comentario.getId(), comentario.getContenido());
        return ComentarioResponse.from(comentario);
    }

    @PatchMapping("/comments/{commentId}")
    @Transactional
    public ComentarioResponse actualizarComentario(@PathVariable Long taskId, @PathVariable Long commentId,
                                                   @Valid @RequestBody ComentarioRequest request,
                                                   Authentication authentication) {
        ComentarioTareaEntity comentario = comentarios.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        if (!taskId.equals(comentario.getTareaId())) throw new IllegalArgumentException("El comentario no pertenece a la tarea");
        if (!actor(authentication).equals(comentario.getAutorId())) throw new org.springframework.security.access.AccessDeniedException("Solo el autor puede editar el comentario");
        comentario.setContenido(request.contenido());
        comentario.setFechaActualizacion(java.time.Instant.now());
        ComentarioTareaEntity saved = comentarios.save(comentario);
        logGlobal(taskId, actor(authentication), "COMENTARIO_ACTUALIZADO", "Comment", commentId, saved.getContenido());
        return ComentarioResponse.from(saved);
    }

    @DeleteMapping("/comments/{commentId}")
    @Transactional
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long taskId, @PathVariable Long commentId,
                                                   Authentication authentication) {
        ComentarioTareaEntity comentario = comentarios.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        if (!taskId.equals(comentario.getTareaId())) throw new IllegalArgumentException("El comentario no pertenece a la tarea");
        if (!actor(authentication).equals(comentario.getAutorId())) throw new org.springframework.security.access.AccessDeniedException("Solo el autor puede eliminar el comentario");
        comentarios.delete(comentario);
        logGlobal(taskId, actor(authentication), "COMENTARIO_ELIMINADO", "Comment", commentId, null);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/files")
    public List<ArchivoResponse> listarArchivos(@PathVariable Long taskId) {
        requireTask(taskId);
        return archivos.findByTareaIdOrderByFechaCreacionDesc(taskId).stream().map(ArchivoResponse::from).toList();
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ArchivoResponse subirArchivo(@PathVariable Long taskId, @RequestPart("file") MultipartFile file,
                                        Authentication authentication) throws IOException {
        requireTask(taskId);
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo no puede estar vacío");
        ArchivoTareaEntity archivo = archivos.save(new ArchivoTareaEntity(taskId, actor(authentication),
                file.getOriginalFilename() == null ? "archivo" : file.getOriginalFilename(),
                file.getContentType() == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : file.getContentType(),
                file.getBytes()));
        logGlobal(taskId, actor(authentication), "ARCHIVO_SUBIDO", "File", archivo.getId(), archivo.getNombre());
        return ArchivoResponse.from(archivo);
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<ByteArrayResource> descargarArchivo(@PathVariable Long taskId, @PathVariable Long fileId) {
        ArchivoTareaEntity archivo = archivo(taskId, fileId);
        MediaType type;
        try { type = MediaType.parseMediaType(archivo.getTipoContenido()); }
        catch (InvalidMediaTypeException ex) { type = MediaType.APPLICATION_OCTET_STREAM; }
        return ResponseEntity.ok().contentType(type).contentLength(archivo.getTamano())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + URLEncoder.encode(archivo.getNombre(), StandardCharsets.UTF_8))
                .body(new ByteArrayResource(archivo.getContenido()));
    }

    @DeleteMapping("/files/{fileId}")
    @Transactional
    public ResponseEntity<Void> eliminarArchivo(@PathVariable Long taskId, @PathVariable Long fileId,
                                                Authentication authentication) {
        ArchivoTareaEntity archivo = archivo(taskId, fileId);
        archivos.delete(archivo);
        logGlobal(taskId, actor(authentication), "ARCHIVO_ELIMINADO", "File", fileId, archivo.getNombre());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activity")
    public List<ActividadTareaEntity> listarActividad(@PathVariable Long taskId) {
        requireTask(taskId);
        return actividad.findByTareaIdOrderByFechaDesc(taskId);
    }

    @GetMapping("/permissions")
    public PermissionsResponse permisos(@PathVariable Long taskId, Authentication authentication) {
        requireTask(taskId);
        return new PermissionsResponse(true, true, true, true, true, true, true, true);
    }

    private ArchivoTareaEntity archivo(Long taskId, Long fileId) {
        ArchivoTareaEntity archivo = archivos.findById(fileId).orElseThrow(() -> new IllegalArgumentException("Archivo no encontrado"));
        if (!taskId.equals(archivo.getTareaId())) throw new IllegalArgumentException("El archivo no pertenece a la tarea");
        return archivo;
    }

    private void requireTask(Long taskId) {
        if (!tareas.existsById(taskId)) throw new IllegalArgumentException("Tarea no encontrada");
    }

    private Long actor(Authentication authentication) {
        return Long.valueOf(authentication.getPrincipal().toString());
    }

    private void logGlobal(Long taskId, Long userId, String action, String resourceType,
                           Long resourceId, String resourceName) {
        Optional<TareaEntity> tareaOpt = tareas.findById(taskId);
        Long projectId = tareaOpt.map(TareaEntity::getProyectoId).orElse(null);
        activityLogService.registrar(userId, action, resourceType, resourceId, resourceName,
                projectId, null, null);
    }

    public record ChecklistRequest(@NotBlank String contenido, int posicion) {}
    public record ChecklistUpdateRequest(String contenido, Boolean completado, Integer posicion) {}
    public record ComentarioRequest(@NotBlank String contenido) {}
    public record ComentarioResponse(Long id, Long autorId, String contenido, java.time.Instant fechaCreacion, java.time.Instant fechaActualizacion) {
        static ComentarioResponse from(ComentarioTareaEntity c) { return new ComentarioResponse(c.getId(), c.getAutorId(), c.getContenido(), c.getFechaCreacion(), c.getFechaActualizacion()); }
    }
    public record ArchivoResponse(Long id, Long subidoPor, String nombre, String tipoContenido, long tamano, java.time.Instant fechaCreacion, String url) {
        static ArchivoResponse from(ArchivoTareaEntity a) { return new ArchivoResponse(a.getId(), a.getSubidoPor(), a.getNombre(), a.getTipoContenido(), a.getTamano(), a.getFechaCreacion(), "/api/tasks/" + a.getTareaId() + "/files/" + a.getId()); }
    }
    public record PermissionsResponse(boolean ver, boolean crear, boolean editar, boolean eliminar,
                                      boolean cambiarEstado, boolean asignarResponsable, boolean comentar,
                                      boolean subirArchivos) {}
}
