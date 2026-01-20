package com.example.proyecto.models;

public class LoginRequest {
    private String correo;
    private String password;

    public LoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    // Getters y Setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}