package com.example.mantenimiento.model.mongo;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Mantenimiento {
    @Id
    private String id;
    private Long id_monopatin;
    private LocalDate inicio;
    private LocalDate fin;
    private double km_recorridos;

    public Mantenimiento(Mantenimiento m) {
        this.id = m.getId();
        this.id_monopatin = m.getId_monopatin();
        this.inicio = m.getInicio();
        this.fin = m.getFin();
        this.km_recorridos = m.getKm_recorridos();
    }

    public Mantenimiento() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getId_monopatin() {
        return id_monopatin;
    }

    public void setId_monopatin(Long id_monopatin) {
        this.id_monopatin = id_monopatin;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public double getKm_recorridos() {
        return km_recorridos;
    }

    public void setKm_recorridos(double km_recorridos) {
        this.km_recorridos = km_recorridos;
    }
}
