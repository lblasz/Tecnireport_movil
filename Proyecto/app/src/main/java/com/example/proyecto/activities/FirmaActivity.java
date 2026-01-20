package com.example.proyecto.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.session.SessionManager;

public class FirmaActivity extends AppCompatActivity {

    private ImageView imgFirma;
    private Button btnConfirmarFirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);

        imgFirma = findViewById(R.id.imgFirma);
        btnConfirmarFirma = findViewById(R.id.btnConfirmarFirma);

        Usuario usuario = SessionManager.getInstance().getUsuario();

        // Mostrar firma según rol
        if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
            imgFirma.setImageResource(R.drawable.timbre_administrador);
        } else {
            imgFirma.setImageResource(R.drawable.timbre_tecnico);
        }

        btnConfirmarFirma.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Firma confirmada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish(); // vuelve a la pantalla anterior
        });
    }
}
