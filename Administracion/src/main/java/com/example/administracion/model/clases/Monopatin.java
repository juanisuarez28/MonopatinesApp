package com.example.administracion.model.clases;

import com.example.administracion.service.dto.MonopatinDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Monopatin {
    private Long id;
    private String numeroSerie;
    private double kilometraje;

    private boolean enMantenimiento;

    private List<Viaje> viajes;

    public Monopatin(Monopatin monopatin) {
        this.id = monopatin.id;
        this.numeroSerie = monopatin.numeroSerie;
        this.kilometraje = monopatin.kilometraje;
        this.enMantenimiento = monopatin.enMantenimiento;
        this.viajes = monopatin.viajes;
    }

    public Monopatin(MonopatinDTO monopatin) {
        this.id = monopatin.getId();
        this.numeroSerie = monopatin.getNumeroSerie();
        this.kilometraje = monopatin.getKilometraje();
        this.enMantenimiento = monopatin.isEnMantenimiento();
    }

    public Monopatin(Long id, String numeroSerie, double kilometraje, boolean enMantenimiento, List<Viaje> viajes) {
        this.id = id;
        this.numeroSerie = numeroSerie;
        this.kilometraje = kilometraje;
        this.enMantenimiento = enMantenimiento;
        this.viajes = viajes;
    }

    public Monopatin() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    public void addViaje(Viaje viaje){
        this.viajes.add(viaje);
    }
}
