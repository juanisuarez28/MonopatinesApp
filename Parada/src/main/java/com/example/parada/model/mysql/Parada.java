package com.example.parada.model.mysql;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double x;
    @Column
    private double y;

    @ElementCollection
    @CollectionTable(name = "monopatines_en_parada")
    @Column(name = "monopatin_id")
    private List<Long> monopatinesEnLaParada;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<Long> getMonopatinesEnLaParada() {
        return monopatinesEnLaParada;
    }

    public void setMonopatinesEnLaParada(List<Long> monopatinesEnLaParada) {
        this.monopatinesEnLaParada = monopatinesEnLaParada;
    }
}
