package com.example.proyecto.models;

public class Establecimiento {

    private String rbd;
    private String nombre;
    private String direccion;

    public Establecimiento(String rbd, String nombre, String direccion) {
        this.rbd = rbd;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getRbd() {
        return rbd;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
