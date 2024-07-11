package com.Hospital.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Estructura de datos utilizada para realizar transacciones con la base de datos en relación a las mascotas
@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especie;
    private String raza;
    private int edad;
    private String codigoIdentificacion;
    private String dniResponsable;
    private Boolean deleted;

    @OneToMany(mappedBy = "mascota")
    private List<Ingreso> ingresos;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getDniResponsable() {
        return dniResponsable;
    }

    public void setDniResponsable(String dniResponsable) {
        this.dniResponsable = dniResponsable;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDelete() {
        return deleted;
    }

    public void addIngreso(Ingreso ingreso) {
        if(this.ingresos == null){
            this.ingresos = new ArrayList<Ingreso>();
        }
        ingresos.add(ingreso);
    }
}