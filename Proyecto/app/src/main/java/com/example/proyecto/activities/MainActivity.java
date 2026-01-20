package com.example.proyecto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.R;
import com.example.proyecto.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ===== SESIÓN =====
        SessionManager session = SessionManager.getInstance();

        if (session.getUsuario() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        //if (session.isAdmin()) {
        //    findViewById(R.id.btnCrearInforme).setVisibility(View.GONE);
        //}

        // Ajuste de márgenes por barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // Botón: Crear Informe
        findViewById(R.id.btnCrearInforme).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CrearInformeActivity.class));
        });

        // Botón: Mis Informes
        findViewById(R.id.btnMisInformes).setOnClickListener(v -> {
            // Más adelante
        });

        // Botón: Cerrar Sesión
        findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> {
            session.cerrarSesion();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
