package com.example.monopatin.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String numeroSerie;
    @Column
    private double kilometraje;
    @Column
    private boolean enMantenimiento;

    @JsonIgnore
    @OneToMany(mappedBy = "monopatin")
    private List<Viaje> viajes;

    public Monopatin(Monopatin monopatin) {
        this.id = monopatin.id;
        this.numeroSerie = monopatin.numeroSerie;
        this.kilometraje = monopatin.kilometraje;
        this.enMantenimiento = monopatin.enMantenimiento;
        this.viajes = monopatin.viajes;
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
