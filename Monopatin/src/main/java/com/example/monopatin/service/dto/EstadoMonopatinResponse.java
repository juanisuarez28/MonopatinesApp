package com.example.monopatin.service.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class EstadoMonopatinResponse implements Serializable {
    private Long enOperacion;
    private Long enMantenimiento;

    public EstadoMonopatinResponse() {
    }

    public EstadoMonopatinResponse(Long enOperacion, Long enMantenimiento) {
        this.enOperacion = enOperacion;
        this.enMantenimiento = enMantenimiento;
    }

    public Long getEnOperacion() {
        return enOperacion;
    }

    public void setEnOperacion(Long enOperacion) {
        this.enOperacion = enOperacion;
    }

    public Long getEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(Long enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }
}