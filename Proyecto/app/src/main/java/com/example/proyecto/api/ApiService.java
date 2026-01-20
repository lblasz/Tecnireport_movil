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
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("establishments")
    Call<List<EstablishmentDTO>> getEstablishments();

    @POST("reports")
    Call<Map<String, Object>> createReport(@Body InformeRequest informe);
}