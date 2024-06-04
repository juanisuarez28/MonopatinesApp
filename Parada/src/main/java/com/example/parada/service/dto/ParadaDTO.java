package com.example.parada.service.dto;

import com.example.parada.model.mysql.Parada;
import lombok.Getter;

@Getter
public class ParadaDTO {

    private long id;
    private double x;
    private double y;

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

