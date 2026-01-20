package com.example.proyecto.models;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData usuario;

    // Clase interna para datos del usuario
    public static class UserData {
        private int id;
        private String nombre;
        private String apellido;
        private String correo;
        private String telefono;
        private String rol;

        // Getters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public String getCorreo() { return correo; }
        public String getTelefono() { return telefono; }
        public String getRol() { return rol; }
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public UserData getUsuario() { return usuario; }
}