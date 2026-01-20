package com.example.proyecto.models;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String rol; // "ADMIN" o "TECNICO"

    public Usuario(int id, String nombre, String apellido, String email, String telefono, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRol() {
        return rol;
    }
}
