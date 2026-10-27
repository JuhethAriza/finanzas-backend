package com.tuapp.finanzas.shared.exception;

import com.tuapp.finanzas.auth.domain.exception.CredencialesInvalidasException;
import com.tuapp.finanzas.project.domain.exception.ProyectoInvalidoException;
import com.tuapp.finanzas.project.domain.exception.ProyectoNoEncontradoException;
import com.tuapp.finanzas.team.domain.exception.MiembroNoEncontradoException;
import com.tuapp.finanzas.team.domain.exception.MiembroYaEnEquipoException;
import com.tuapp.finanzas.team.domain.exception.TeamNoEncontradoException;
import com.tuapp.finanzas.user.domain.exception.EmailInvalidoException;
import com.tuapp.finanzas.user.domain.exception.EmailYaRegistradoException;
import com.tuapp.finanzas.user.domain.exception.UsuarioNoEncontradoException;
import com.tuapp.finanzas.task.domain.exception.TareaInvalidaException;
import com.tuapp.finanzas.task.domain.exception.TareaNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TareaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleTareaNoEncontrada(TareaNoEncontradaException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TareaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleTareaInvalida(TareaInvalidaException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleEmailInvalido(EmailInvalidoException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<Map<String, Object>> handleEmailYaRegistrado(EmailYaRegistradoException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos. Revisa el formulario.");
        return construirRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Datos inválidos. Revisa el formulario.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleNoLegible(HttpMessageNotReadableException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Datos inválidos. Revisa el formulario.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return construirRespuesta(HttpStatus.FORBIDDEN, "No tienes permisos para esta acción.");
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(TeamNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleTeamNoEncontrado(TeamNoEncontradoException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MiembroYaEnEquipoException.class)
    public ResponseEntity<Map<String, Object>> handleMiembroYaEnEquipo(MiembroYaEnEquipoException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MiembroNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleMiembroNoEncontrado(MiembroNoEncontradoException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProyectoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleProyectoNoEncontrado(ProyectoNoEncontradoException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProyectoInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleProyectoInvalido(ProyectoInvalidoException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "El servidor no responde. Intenta más tarde.");
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", mensaje);
        return ResponseEntity.status(status).body(body);
    }
}
