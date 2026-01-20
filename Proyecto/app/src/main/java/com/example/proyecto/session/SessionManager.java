package com.example.proyecto.session;

import com.example.proyecto.models.Usuario;

public class SessionManager {

    private static SessionManager instance;
    private Usuario usuarioActual;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuario() {
        return usuarioActual;
    }

    public boolean isAdmin() {
        return usuarioActual != null && usuarioActual.getRol().equals("ADMIN");
    }

    public boolean isTecnico() {
        return usuarioActual != null && usuarioActual.getRol().equals("TECNICO");
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }
}
