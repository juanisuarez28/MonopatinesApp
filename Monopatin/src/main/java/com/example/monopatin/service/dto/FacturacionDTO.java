package com.example.monopatin.service.dto;

import java.math.BigDecimal;

public class FacturacionDTO {
    private Integer sumaPrecio;

    public FacturacionDTO(Integer sumaPrecio) {
        this.sumaPrecio = sumaPrecio;
    }

    public Integer getSumaPrecio() {
        return sumaPrecio;
    }

    public void setSumaPrecio(Integer sumaPrecio) {
        this.sumaPrecio = sumaPrecio;
    }
}
