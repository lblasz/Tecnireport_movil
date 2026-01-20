package com.example.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    /**
     * Valida los datos del login.
     * @return null si todo está correcto, o un mensaje de error.
     */
    public String validar(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            return "Debe ingresar su correo institucional";
        }

        if (!email.endsWith("@demovalle.cl")) {
            return "El correo debe terminar en @demovalle.cl";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Debe ingresar la contraseña";
        }

        if (password.length() <6) {
            return "La contraseña debe tener al menos 6 caracteres";
        }

        return null; // ✅ Todo correcto
    }
}
