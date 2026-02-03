package com.example.proyecto.api;

import com.example.proyecto.models.LoginRequest;
import com.example.proyecto.models.LoginResponse;
import com.example.proyecto.models.InformeRequest;
import com.example.proyecto.models.EstablishmentDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("establishments")
    Call<List<EstablishmentDTO>> getEstablishments();

    @POST("reports")
    Call<Map<String, Object>> createReport(@Body InformeRequest informe);

    @Multipart
    @POST("reports")
    Call<Map<String, Object>> createReportWithPhoto(
            @Part("usuario_id") RequestBody usuarioId,
            @Part("rbd_establecimiento") RequestBody rbd,
            @Part("nombre_establecimiento") RequestBody nombre,
            @Part("direccion") RequestBody direccion,
            @Part("hora_entrada") RequestBody horaEntrada,
            @Part("hora_salida") RequestBody horaSalida,
            @Part("tipo_equipo") RequestBody tipoEquipo,
            @Part("motivo_visita") RequestBody motivoVisita,
            @Part("diagnostico") RequestBody diagnostico,
            @Part("observaciones") RequestBody observaciones,
            @Part("firma_responsable") RequestBody firmaResponsable,
            @Part("firma_establecimiento") RequestBody firmaEstablecimiento,
            @Part("firma_rol") RequestBody firmaRol,
            @Part MultipartBody.Part foto
    );
}