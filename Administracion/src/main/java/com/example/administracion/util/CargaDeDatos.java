package com.example.administracion.util;

import com.example.administracion.model.mysql.Administrador;
import com.example.administracion.repository.mysql.AdministracionRepo;
import com.example.administracion.service.ServicioAdministracion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@Component
public class CargaDeDatos {

    private final AdministracionRepo ar;
    private ServicioAdministracion sa;

    @Autowired
    public CargaDeDatos(AdministracionRepo ar) {
        this.ar = ar;
    }

    public void cargarDatosDesdeCSV() throws IOException{

        CSVParser datosAdministracion = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Administracion\\src\\main\\java\\com\\example\\administracion\\csv\\Administracion.csv"));

        for (CSVRecord administrador: datosAdministracion) {
            Administrador a = new Administrador();

            a.setId(Long.valueOf(administrador.get("id")));
            a.setNombre(administrador.get("nombre"));
            ar.save(a);

        }
    }
}
