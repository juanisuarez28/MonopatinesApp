package com.example.administracion.service.dto;

import com.example.administracion.model.clases.Monopatin;
import com.example.administracion.model.clases.Viaje;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class MonopatinDTO implements Serializable {
    private Long id;
    private String numeroSerie;
    private double kilometraje;
    private boolean enMantenimiento;


    public MonopatinDTO() {
    }

    public MonopatinDTO(Long id, String numeroSerie, double kilometraje, boolean enMantenimiento) {
        this.id = id;
        this.numeroSerie = numeroSerie;
        this.kilometraje = kilometraje;
        this.enMantenimiento = enMantenimiento;
    }

    public MonopatinDTO(Monopatin m){
        this.id = m.getId();
        this.numeroSerie = m.getNumeroSerie();
        this.kilometraje = m.getKilometraje();
        this.enMantenimiento = m.isEnMantenimiento();
    }


    public Long getId() {
        return id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }


}
