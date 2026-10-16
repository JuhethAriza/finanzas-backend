package com.tuapp.finanzas.team.controller;

import com.tuapp.finanzas.team.domain.model.Team;
import com.tuapp.finanzas.team.domain.model.TeamMember;
import com.tuapp.finanzas.team.domain.port.in.TeamMemberUseCase;
import com.tuapp.finanzas.team.domain.port.in.TeamUseCase;
import com.tuapp.finanzas.team.dto.request.AgregarMiembroRequest;
import com.tuapp.finanzas.team.dto.request.ActualizarTeamRequest;
import com.tuapp.finanzas.team.dto.request.CrearTeamRequest;
import com.tuapp.finanzas.team.dto.response.TeamMemberResponse;
import com.tuapp.finanzas.team.dto.response.TeamResponse;
import com.tuapp.finanzas.user.domain.model.Usuario;
import com.tuapp.finanzas.user.domain.port.out.UsuarioRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamUseCase teamUseCase;
    private final TeamMemberUseCase teamMemberUseCase;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public TeamController(TeamUseCase teamUseCase, TeamMemberUseCase teamMemberUseCase, UsuarioRepositoryPort usuarioRepositoryPort) {
        this.teamUseCase = teamUseCase;
        this.teamMemberUseCase = teamMemberUseCase;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> listar() {
        List<TeamResponse> teams = teamUseCase.listarTodos().stream()
                .map(TeamResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> crear(@RequestBody @Valid CrearTeamRequest request) {
        Team team = teamUseCase.crear(request.nombre(), request.descripcion());
        return ResponseEntity.status(201).body(TeamResponse.from(team));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(TeamResponse.from(teamUseCase.obtenerPorId(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamResponse> actualizar(@PathVariable Long id, @RequestBody @Valid ActualizarTeamRequest request) {
        Team team = teamUseCase.actualizar(id, request.nombre(), request.descripcion());
        return ResponseEntity.ok(TeamResponse.from(team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        teamUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<TeamMemberResponse>> listarMiembros(@PathVariable Long id) {
        List<TeamMember> members = teamMemberUseCase.listarMiembros(id);

        // Resolve user names and emails
        Map<Long, Usuario> userMap = members.stream()
                .map(m -> m.getUserId())
                .distinct()
                .map(uid -> usuarioRepositoryPort.buscarPorId(uid).orElse(null))
                .filter(u -> u != null)
                .collect(Collectors.toMap(Usuario::getId, u -> u));

        List<TeamMemberResponse> responses = members.stream()
                .map(m -> {
                    Usuario user = userMap.get(m.getUserId());
                    String nombre = user != null ? user.getNombre() : null;
                    String email = user != null ? user.getEmail() : null;
                    return TeamMemberResponse.from(m, nombre, email);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<TeamMemberResponse> agregarMiembro(@PathVariable Long id, @RequestBody @Valid AgregarMiembroRequest request) {
        TeamMember member = teamMemberUseCase.agregarMiembro(id, request.userId(), request.rol());
        Usuario user = usuarioRepositoryPort.buscarPorId(request.userId()).orElse(null);
        return ResponseEntity.status(201).body(TeamMemberResponse.from(member,
                user != null ? user.getNombre() : null,
                user != null ? user.getEmail() : null));
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id, @PathVariable Long userId) {
        teamMemberUseCase.eliminarMiembro(id, userId);
        return ResponseEntity.noContent().build();
    }
}