package com.example.usuariocuenta;

import com.example.usuariocuenta.util.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication

public class UsuarioCuentaApplication {

    @Autowired
    private CargaDeDatos cargaDeDatos;

    public UsuarioCuentaApplication() {

    }

    public static void main(String[] args) {
        SpringApplication.run(UsuarioCuentaApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }

}
