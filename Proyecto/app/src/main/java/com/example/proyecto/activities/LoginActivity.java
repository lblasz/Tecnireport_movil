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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.proyecto.api.RetrofitClient;
import com.example.proyecto.models.LoginRequest;
import com.example.proyecto.models.LoginResponse;

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
            String usuarioInput = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = usuarioInput + "@demovalle.cl";

            // Validación
            String error = viewModel.validar(email, password);
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                return;
            }

            // ===== LOGIN REAL CON API =====
            LoginRequest request = new LoginRequest(email, password);

            RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();

                        if (loginResponse.isSuccess()) {
                            // Crear usuario con datos reales de la BD
                            Usuario usuario = new Usuario(
                                    loginResponse.getUsuario().getId(),
                                    loginResponse.getUsuario().getNombre(),
                                    loginResponse.getUsuario().getApellido(),
                                    loginResponse.getUsuario().getCorreo(),
                                    loginResponse.getUsuario().getTelefono(),
                                    loginResponse.getUsuario().getRol()
                            );

                            SessionManager.getInstance().setUsuario(usuario);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    loginResponse.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Error de autenticación",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,
                            "Error de conexión: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
