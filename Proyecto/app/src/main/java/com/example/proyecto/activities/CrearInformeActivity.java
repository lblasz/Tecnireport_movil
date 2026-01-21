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
import java.util.Map;

import com.example.proyecto.api.RetrofitClient;
import com.example.proyecto.models.EstablishmentDTO;
import com.example.proyecto.models.InformeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        RetrofitClient.getApiService().getEstablishments().enqueue(new Callback<List<EstablishmentDTO>>() {
            @Override
            public void onResponse(Call<List<EstablishmentDTO>> call, Response<List<EstablishmentDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EstablishmentDTO> establecimientosDTO = response.body();

                    // Convertir a modelo Establecimiento local
                    establecimientos = new ArrayList<>();
                    for (EstablishmentDTO dto : establecimientosDTO) {
                        establecimientos.add(new Establecimiento(
                                dto.getRbd(),
                                dto.getNombre(),
                                dto.getDireccion()
                        ));
                    }

                    // Configurar AutoCompleteTextView
                    ArrayAdapter<Establecimiento> adapter = new ArrayAdapter<>(
                            CrearInformeActivity.this,
                            android.R.layout.simple_dropdown_item_1line,
                            establecimientos
                    );
                    actEstablecimiento.setAdapter(adapter);

                    actEstablecimiento.setOnItemClickListener((parent, view, position, id) -> {
                        Establecimiento seleccionado = (Establecimiento) parent.getItemAtPosition(position);
                        etRbd.setText(seleccionado.getRbd());
                        etDireccion.setText(seleccionado.getDireccion());

                        informe.setNombreEstablecimiento(seleccionado.getNombre());
                        informe.setRbd(seleccionado.getRbd());
                        informe.setDireccion(seleccionado.getDireccion());
                    });
                } else {
                    Toast.makeText(CrearInformeActivity.this,
                            "Error al cargar establecimientos",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EstablishmentDTO>> call, Throwable t) {
                Toast.makeText(CrearInformeActivity.this,
                        "Error de conexión al cargar establecimientos",
                        Toast.LENGTH_SHORT).show();
            }
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

        enviarInformeAlBackend();

        // 🔒 PRÓXIMO FLUJO
        // - Habilitar exportación
        // - Enviar a API
    }

    private void enviarInformeAlBackend() {
        // Preparar datos
        InformeRequest request = new InformeRequest();
        Usuario usuario = SessionManager.getInstance().getUsuario();

        request.setUsuarioId(usuario.getId());
        request.setRbdEstablecimiento(informe.getRbd());
        request.setNombreEstablecimiento(informe.getNombreEstablecimiento());
        request.setDireccion(informe.getDireccion());
        request.setHoraEntrada(informe.getFechaHoraInicio());
        request.setHoraSalida(informe.getFechaHoraFin());
        request.setTipoEquipo(informe.getTipoEquipo());
        request.setMotivoVisita(informe.getMotivoVisita());
        request.setDiagnostico(informe.getDiagnostico());
        request.setObservaciones(informe.getObservaciones());
        request.setFirmaResponsable(true);
        request.setFirmaEstablecimiento(false);
        request.setFirmaRol(usuario.getRol());

        // Enviar al servidor
        RetrofitClient.getApiService().createReport(request).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CrearInformeActivity.this,
                            "Informe enviado correctamente",
                            Toast.LENGTH_LONG).show();

                    // Volver al menú principal
                    startActivity(new Intent(CrearInformeActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(CrearInformeActivity.this,
                            "Error al enviar informe",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(CrearInformeActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
