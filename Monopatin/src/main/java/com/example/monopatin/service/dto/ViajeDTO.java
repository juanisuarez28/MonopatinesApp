package com.example.monopatin.service.dto;

import com.example.monopatin.model.mysql.Viaje;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class ViajeDTO implements Serializable {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double kilometrosRecorridos;

    public ViajeDTO(Long id, LocalDate fechaInicio, LocalDate fechaFin, double kilometrosRecorridos) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public ViajeDTO(Viaje r) {
        this.id = r.getId();
        this.fechaInicio = r.getFechaInicio();
        this.fechaFin = r.getFechaFin();
        this.kilometrosRecorridos = r.getKilometrosRecorridos();
    }


}
