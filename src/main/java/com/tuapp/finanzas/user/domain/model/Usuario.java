package com.tuapp.finanzas.user.domain.model;

public class Usuario {

    private final Long id;
    private String nombre;
    private String email;
    private String passwordHash;

    private Usuario(Long id, String nombre, String email, String passwordHash) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public static Usuario crear(String nombre, String email, String passwordHash) {
        if (email == null || !email.contains("@")) {
            throw new com.tuapp.finanzas.user.domain.exception.EmailInvalidoException("Email inválido: " + email);
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return new Usuario(null, nombre, email, passwordHash);
    }

    public static Usuario reconstruir(Long id, String nombre, String email, String passwordHash) {
        return new Usuario(id, nombre, email, passwordHash);
    }

    public void actualizarNombre(String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nuevoNombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
}