package com.example.proyecto.models;

public class InformeRequest {
    private int usuario_id;
    private String rbd_establecimiento;
    private String nombre_establecimiento;
    private String direccion;
    private String hora_entrada;
    private String hora_salida;
    private String tipo_equipo;
    private String motivo_visita;
    private String diagnostico;
    private String observaciones;
    private boolean firma_responsable;
    private boolean firma_establecimiento;
    private String firma_rol;

    // Constructor vacío
    public InformeRequest() {}

    public int getUsuarioId() { return usuario_id; }
    public void setUsuarioId(int usuario_id) { this.usuario_id = usuario_id; }

    public String getRbdEstablecimiento() { return rbd_establecimiento; }
    public void setRbdEstablecimiento(String rbd) { this.rbd_establecimiento = rbd; }

    public String getNombreEstablecimiento() { return nombre_establecimiento; }
    public void setNombreEstablecimiento(String nombre) { this.nombre_establecimiento = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getHoraEntrada() { return hora_entrada; }
    public void setHoraEntrada(String hora) { this.hora_entrada = hora; }

    public String getHoraSalida() { return hora_salida; }
    public void setHoraSalida(String hora) { this.hora_salida = hora; }

    public String getTipoEquipo() { return tipo_equipo; }
    public void setTipoEquipo(String tipo) { this.tipo_equipo = tipo; }

    public String getMotivoVisita() { return motivo_visita; }
    public void setMotivoVisita(String motivo) { this.motivo_visita = motivo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String obs) { this.observaciones = obs; }

    public boolean isFirmaResponsable() { return firma_responsable; }
    public void setFirmaResponsable(boolean firma) { this.firma_responsable = firma; }

    public boolean isFirmaEstablecimiento() { return firma_establecimiento; }
    public void setFirmaEstablecimiento(boolean firma) { this.firma_establecimiento = firma; }

    public String getFirmaRol() { return firma_rol; }
    public void setFirmaRol(String rol) { this.firma_rol = rol; }
}