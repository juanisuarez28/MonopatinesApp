package com.example.parada;

import com.example.parada.util.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication

public class ParadaApplication {


    private CargaDeDatos cargaDeDatos;
    @Autowired
    public ParadaApplication(CargaDeDatos c) {
        this.cargaDeDatos=c;

    }

    public static void main(String[] args) {
        SpringApplication.run(ParadaApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }

}
