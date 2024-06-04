package com.example.monopatin;

import com.example.monopatin.util.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MonopatinApplication {


    private CargaDeDatos cargaDeDatos;
    @Autowired
    public MonopatinApplication(CargaDeDatos c) {
        this.cargaDeDatos=c;

    }
    public static void main(String[] args) {
        SpringApplication.run(MonopatinApplication.class, args);
    }


    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }
}
