package com.tuapp.finanzas.user.domain.port.in;

import com.tuapp.finanzas.user.domain.model.Usuario;
import java.util.List;

public interface UsuarioUseCase {
    Usuario crear(String nombre, String email, String passwordPlano);
    Usuario obtenerPorId(Long id);
    List<Usuario> listarTodos();
    Usuario actualizarNombre(Long id, String nuevoNombre);
    void eliminar(Long id);
}