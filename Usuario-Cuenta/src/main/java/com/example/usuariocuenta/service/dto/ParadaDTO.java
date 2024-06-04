package com.example.usuariocuenta.service.dto;

import com.example.usuariocuenta.model.clases.Parada;
import lombok.Getter;

import java.util.List;

@Getter
public class ParadaDTO {

    private long id;
    private double x;
    private double y;

    public ParadaDTO() {
    }

    public ParadaDTO(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public ParadaDTO(Parada p){
        this.id = p.getId();
        this.x = p.getX();
        this.y = p.getY();
    }

    public long getId() {
        return id;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }



}

