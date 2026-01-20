package com.example.proyecto.models;

public class EstablishmentDTO {
    private String RBD_establecimiento;
    private String Nombre_Establecimiento;
    private String Direccion;


    public String getRbd() { return RBD_establecimiento; }
    public String getNombre() { return Nombre_Establecimiento; }
    public String getDireccion() { return Direccion; }

    @Override
    public String toString() {
        return Nombre_Establecimiento;
    }
}