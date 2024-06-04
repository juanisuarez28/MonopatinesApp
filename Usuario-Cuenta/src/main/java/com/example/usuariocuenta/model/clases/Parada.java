package com.example.usuariocuenta.model.clases;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Parada {

    private long id;
    private double x;
    private double y;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }
}
