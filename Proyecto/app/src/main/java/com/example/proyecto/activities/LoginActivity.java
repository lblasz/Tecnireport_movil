package com.example.proyecto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.session.SessionManager;
import com.example.proyecto.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ===== Bind UI =====
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        viewModel = new LoginViewModel();

        btnLogin.setOnClickListener(v -> {

            // El usuario SOLO escribe el nombre (ej: admin / tecnico)
            String usuarioInput = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Construir correo automáticamente
            String email = usuarioInput + "@demovalle.cl";

            // ===== Validación =====
            String error = viewModel.validar(email, password);
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                return;
            }

            // ===== LOGIN SIMULADO (TEMPORAL) =====
            Usuario usuario;

            if (email.equalsIgnoreCase("admin@demovalle.cl")) {

                usuario = new Usuario(
                        1,
                        "Administrador",
                        "Sistema",
                        email,
                        "123456789",
                        "ADMIN"
                );

            } else if (email.equalsIgnoreCase("tecnico@demovalle.cl")) {

                usuario = new Usuario(
                        2,
                        "Patrick",
                        "Vera",
                        email,
                        "987654321",
                        "TECNICO"
                );

            } else {
                Toast.makeText(
                        this,
                        "Usuario no registrado",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // ===== Guardar sesión =====
            SessionManager.getInstance().setUsuario(usuario);

            // ===== Ir al menú principal =====
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
