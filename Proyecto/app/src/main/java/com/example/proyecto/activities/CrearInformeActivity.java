package com.example.proyecto.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.Establecimiento;
import com.example.proyecto.models.Informe;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.session.SessionManager;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrearInformeActivity extends AppCompatActivity {

    private static final int REQUEST_FOTO = 100;

    private Informe informe;

    // UI
    private TextView txtFechaHora;
    private MaterialAutoCompleteTextView actEstablecimiento;
    private EditText etRbd, etDireccion, etEquipo, etMotivo, etDiagnostico, etObservaciones;
    private ImageView imgEvidencia;
    private List<Establecimiento> establecimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_informe);

        // ===== Bind UI =====
        txtFechaHora = findViewById(R.id.txtFechaHora);
        actEstablecimiento = findViewById(R.id.actEstablecimiento);
        etRbd = findViewById(R.id.etRbd);
        etDireccion = findViewById(R.id.etDireccion);
        etEquipo = findViewById(R.id.etEquipo);
        etMotivo = findViewById(R.id.etMotivo);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        etObservaciones = findViewById(R.id.etObservaciones);
        imgEvidencia = findViewById(R.id.imgEvidencia);

        // Bloquear edición manual (se autocompletan)
        etRbd.setEnabled(false);
        etDireccion.setEnabled(false);

        // ===== Crear informe =====
        informe = new Informe();

        // Fecha y hora automática (inicio)
        String fechaHoraInicio = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        ).format(new Date());

        txtFechaHora.setText("Fecha y hora: " + fechaHoraInicio);
        informe.setFechaHoraInicio(fechaHoraInicio);

        // Técnico desde sesión (NO visible en el formulario)
        Usuario tecnico = SessionManager.getInstance().getUsuario();
        informe.setTecnico(tecnico);

        // ===== Texto predictivo establecimientos =====
        cargarEstablecimientos();

        // ----- Botón: Agregar evidencia   -----
        findViewById(R.id.btnAgregarFoto).setOnClickListener(v -> abrirCamara());

        // ===== Botón Guardar  =====
        findViewById(R.id.btnSiguiente).setOnClickListener(v -> guardarInforme());

        // ===== Botón Finalizar =====
        findViewById(R.id.btnFinalizar).setOnClickListener(v -> finalizarInforme());
    }

    // =========================
    // TEXTO PREDICTIVO
    // =========================
    private void cargarEstablecimientos() {

        establecimientos = new ArrayList<>();

        establecimientos.add(new Establecimiento(
                "13591-7",
                "CENTRO INTEGRAL DE ADULTOS LIMARI",
                "LIBERTAD Nº 520 OVALLE"
        ));

        establecimientos.add(new Establecimiento(
                "13387-6",
                "COLEGIO DE ARTES ELISEO VIDELA JORQUERA",
                "AV. LA CHIMBA S/N OVALLE"
        ));

        establecimientos.add(new Establecimiento(
                "706-4",
                "COLEGIO FRAY JORGE",
                "VICTORIA #533"
        ));

        establecimientos.add(new Establecimiento(
                "40126-9",
                "COLEGIO RAUL SILVA HENRIQUEZ",
                "AV. TUQUI #1255 VILLA LAS TORRES OVALLE"
        ));

        establecimientos.add(new Establecimiento(
                "11136-8",
                "COLEGIO SAN ALBERTO HURTADO CRUCHAGA",
                "CALLE TOCOPILLA S/N OVALLE"
        ));

        ArrayAdapter<Establecimiento> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                establecimientos
        );

        actEstablecimiento.setAdapter(adapter);

        actEstablecimiento.setOnItemClickListener((parent, view, position, id) -> {

            Establecimiento seleccionado =
                    (Establecimiento) parent.getItemAtPosition(position);

            // Autocompletar campos
            etRbd.setText(seleccionado.getRbd());
            etDireccion.setText(seleccionado.getDireccion());

            // Guardar en el modelo
            informe.setNombreEstablecimiento(seleccionado.getNombre());
            informe.setRbd(seleccionado.getRbd());
            informe.setDireccion(seleccionado.getDireccion());
        });
    }

    // =========================
    // EVIDENCIA FOTOGRÁFICA
    // =========================
    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOTO && resultCode == RESULT_OK && data != null) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            imgEvidencia.setImageBitmap(foto);
            imgEvidencia.setVisibility(View.VISIBLE);
        }
    }

    // =========================
    // BLOQUEAR FORMULARIO
    // =========================
    private void bloquearFormulario() {

        actEstablecimiento.setEnabled(false);
        etEquipo.setEnabled(false);
        etMotivo.setEnabled(false);
        etDiagnostico.setEnabled(false);
        etObservaciones.setEnabled(false);
    }


    // =========================
    // GUARDAR
    // =========================
    private void guardarInforme() {

        if (actEstablecimiento.getText().toString().isEmpty() ||
                etEquipo.getText().toString().isEmpty() ||
                etMotivo.getText().toString().isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete los campos obligatorios",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        informe.setTipoEquipo(etEquipo.getText().toString());
        informe.setMotivoVisita(etMotivo.getText().toString());
        informe.setDiagnostico(etDiagnostico.getText().toString());
        informe.setObservaciones(etObservaciones.getText().toString());

        Toast.makeText(
                this,
                "Informe guardado (pendiente)",
                Toast.LENGTH_SHORT
        ).show();
    }

    // =========================
    // FINALIZAR INFORME
    // =========================
    private void finalizarInforme() {

        if (imgEvidencia.getVisibility() != View.VISIBLE) {
            Toast.makeText(this,
                    "Debe agregar evidencia fotográfica",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        // Hora de salida automática
        String fechaHoraFin = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        ).format(new Date());

        informe.setFechaHoraFin(fechaHoraFin);
        informe.setEstado("FINALIZADO");

        // ===== ASIGNAR FIRMA SEGÚN ROL =====
        Usuario usuario = SessionManager.getInstance().getUsuario();

        if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
            informe.setFirmaAdministrador("TIMBRE_ADMIN"); // luego será imagen/base64
        } else {
            informe.setFirmaTecnico("TIMBRE_TECNICO");
        }


        bloquearFormulario();

        Toast.makeText(
                this,
                "Informe finalizado correctamente",
                Toast.LENGTH_LONG
        ).show();

        // ===== IR A FIRMA DIGITAL =====
        Intent intent = new Intent(
                CrearInformeActivity.this,
                FirmaActivity.class
        );

        // (Opcional a futuro: pasar ID del informe)
        // intent.putExtra("ID_INFORME", informe.getIdInforme());

        startActivity(intent);

        // 🔒 PRÓXIMO FLUJO
        // - Habilitar exportación
        // - Enviar a API
    }
}
