package com.example.administracion.model.clases;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Parada implements Serializable {

    private long id;
    private Long x;
    private Long y;
    private List<Long> monopatinesEnLaParada;

    public Parada() {
    }

    public Parada(long id, Long x, Long y, List<Long> monopatinesEnLaParada) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.monopatinesEnLaParada = monopatinesEnLaParada;
    }

    public Parada(Parada p){
        this.id = p.getId();
        this.x = p.getX();
        this.y = p.getY();
        this.monopatinesEnLaParada = p.getMonopatinesEnLaParada();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public List<Long> getMonopatinesEnLaParada() {
        return monopatinesEnLaParada;
    }

    public void setMonopatinesEnLaParada(List<Long> monopatinesEnLaParada) {
        this.monopatinesEnLaParada = monopatinesEnLaParada;
    }
}

