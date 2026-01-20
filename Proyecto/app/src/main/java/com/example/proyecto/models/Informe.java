package com.example.proyecto.models;

public class Informe {

    // Identificación
    private int idInforme;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String estado; // PENDIENTE / FINALIZADO

    // Establecimiento
    private String nombreEstablecimiento;
    private String rbd;
    private String direccion;

    // Trabajo técnico
    private String tipoEquipo;
    private String motivoVisita;
    private String diagnostico;
    private String observaciones;

    // Técnico responsable
    private Usuario tecnico;

    // Firmas (rutas o base64 a futuro)
    private String firmaTecnico;
    private String firmaAdministrador;

    // 👉 NUEVO: Rol que firma el informe
    // ADMIN o TECNICO
    private String firmaRol;

    // Constructor vacío (IMPORTANTE para API / BD)
    public Informe() {
        this.estado = "PENDIENTE";
    }

    // ===== Getters y Setters =====

    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getRbd() {
        return rbd;
    }

    public void setRbd(String rbd) {
        this.rbd = rbd;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public String getFirmaTecnico() {
        return firmaTecnico;
    }

    public void setFirmaTecnico(String firmaTecnico) {
        this.firmaTecnico = firmaTecnico;
    }

    public String getFirmaAdministrador() {
        return firmaAdministrador;
    }

    public void setFirmaAdministrador(String firmaAdministrador) {
        this.firmaAdministrador = firmaAdministrador;
    }

    // ===== NUEVO =====
    public String getFirmaRol() {
        return firmaRol;
    }

    public void setFirmaRol(String firmaRol) {
        this.firmaRol = firmaRol;
    }
}
