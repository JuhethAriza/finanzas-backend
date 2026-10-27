package com.tuapp.finanzas.calendar.service;

import com.tuapp.finanzas.calendar.domain.model.CalendarEvent;
import com.tuapp.finanzas.calendar.domain.port.in.CalendarUseCase;
import com.tuapp.finanzas.project.domain.model.Project;
import com.tuapp.finanzas.project.domain.port.out.ProjectRepositoryPort;
import com.tuapp.finanzas.task.domain.model.Tarea;
import com.tuapp.finanzas.task.domain.port.out.TareaRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CalendarService implements CalendarUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;
    private final TareaRepositoryPort tareaRepositoryPort;

    public CalendarService(ProjectRepositoryPort projectRepositoryPort, TareaRepositoryPort tareaRepositoryPort) {
        this.projectRepositoryPort = projectRepositoryPort;
        this.tareaRepositoryPort = tareaRepositoryPort;
    }

    @Override
    public List<CalendarEvent> obtenerEventos(Instant startDate, Instant endDate, Long projectId, Long userId) {
        List<CalendarEvent> eventos = new ArrayList<>();

        // Si se filtra por userId, no tiene sentido traer proyectos (Project no tiene "asignado a usuario")
        if (userId == null) {
            List<Project> proyectos = projectRepositoryPort.findByRangoFechas(startDate, endDate, projectId);
            for (Project p : proyectos) {
                eventos.add(new CalendarEvent(
                        p.getId(), p.getNombre(), CalendarEvent.TipoEvento.PROJECT,
                        p.getFechaInicio(), p.getFechaFin(), p.getId()
                ));
            }
        }

        List<Tarea> tareas = tareaRepositoryPort.findByRangoFechas(startDate, endDate, projectId, userId);
        for (Tarea t : tareas) {
            eventos.add(new CalendarEvent(
                    t.getId(), t.getTitulo(), CalendarEvent.TipoEvento.TASK,
                    t.getFechaInicio(), t.getFechaFin(), t.getProyectoId()
            ));
        }

        eventos.sort(Comparator.comparing(CalendarEvent::getStart));
        return eventos;
    }
}
