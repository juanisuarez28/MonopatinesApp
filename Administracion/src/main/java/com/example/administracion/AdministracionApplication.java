package com.example.administracion;

import com.example.administracion.util.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdministracionApplication {

    private CargaDeDatos cargaDeDatos;
    @Autowired
    public AdministracionApplication(CargaDeDatos c) {
        this.cargaDeDatos=c;

    }

    public static void main(String[] args) {
        SpringApplication.run(AdministracionApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }
}
