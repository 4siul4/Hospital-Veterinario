package com.Hospital.dto;

import java.time.LocalDate;

//Se crea la estructura de datos para recibir y enviar datos de ingresos a través de las llamadas API
public class IngresoDTO {
    private Long id;
    private LocalDate fechaAlta;
    private LocalDate fechaFin;
    private String estado;
    private Long mascotaId;
    private String dniPersonaRegistra;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public String getDniPersonaRegistra() {
        return dniPersonaRegistra;
    }

    public void setDniPersonaRegistra(String dniPersonaRegistra) {
        this.dniPersonaRegistra = dniPersonaRegistra;
    }
}