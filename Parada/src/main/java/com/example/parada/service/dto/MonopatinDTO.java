package com.example.parada.service.dto;

public class MonopatinDTO {

    private Long id;
    private double x;
    private double y;

    public MonopatinDTO(Long id, double x, double y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    public Long getId(){
        return this.id;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}
