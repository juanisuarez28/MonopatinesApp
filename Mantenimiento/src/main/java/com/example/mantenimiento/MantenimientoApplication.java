package com.example.mantenimiento;

import com.example.mantenimiento.util.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class MantenimientoApplication {
    private CargaDeDatos cargaDeDatos;

    @Autowired
    public MantenimientoApplication(CargaDeDatos c) {
        this.cargaDeDatos=c;

    }
    public static void main(String[] args) {
        SpringApplication.run(MantenimientoApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }

}
