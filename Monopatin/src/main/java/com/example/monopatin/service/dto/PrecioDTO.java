package com.example.monopatin.service.dto;

import com.example.monopatin.model.mysql.Precio;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class PrecioDTO  implements Serializable {
    private Long id;
    private String clave;
    private double valor;
    private LocalDate fechaInicio;

    private Double valorPorPausaExtendida;


    public PrecioDTO(Long id, String clave, double valor, LocalDate fechaInicio, Double valorPorPausaExtendida) {
        this.id = id;
        this.clave = clave;
        this.valor = valor;
        this.fechaInicio = fechaInicio;
        this.valorPorPausaExtendida=valorPorPausaExtendida;
    }

    public PrecioDTO(Precio p){
        this.id = p.getId();
        this.clave = p.getClave();
        this.valor = p.getValor();
        this.fechaInicio = p.getFechaInicio();
        this.valorPorPausaExtendida= p.getValorPorPausaExtendida();
    }

    public Long getId() {
        return id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Double getValorPorPausaExtendida() {
        return valorPorPausaExtendida;
    }

    public void setValorPorPausaExtendida(Double valorPorPausaExtendida) {
        this.valorPorPausaExtendida = valorPorPausaExtendida;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
}
