package com.Hospital.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

//Estructura de datos utilizada para realizar transacciones con la base de datos en relación a los ingresos
@Entity
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAlta;

    private LocalDate fechaFin;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    private String dniPersonaRegistra;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getDniPersonaRegistra() {
        return dniPersonaRegistra;
    }

    public void setDniPersonaRegistra(String dniPersonaRegistra) {
        this.dniPersonaRegistra = dniPersonaRegistra;
    }
}