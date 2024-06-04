package com.example.mantenimiento.util;

import com.example.mantenimiento.model.mongo.Mantenimiento;
import com.example.mantenimiento.repository.mongo.MantenimientoRepositorioMongodb;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CargaDeDatos {

    private final MantenimientoRepositorioMongodb mr;

    @Autowired
    public CargaDeDatos(MantenimientoRepositorioMongodb mr) {
        this.mr = mr;
    }

    public void cargarDatosDesdeCSV() throws IOException {

        CSVParser datosMantenimiento = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Mantenimiento\\src\\main\\java\\com\\example\\mantenimiento\\csv\\Mantenimiento.csv"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (CSVRecord mantenimiento : datosMantenimiento){
            Mantenimiento m = new Mantenimiento();

            m.setId(mantenimiento.get("idMantenimiento"));
            m.setId_monopatin(Long.valueOf(mantenimiento.get("idMonopatin")));
            m.setInicio(LocalDate.parse(mantenimiento.get("inicio"),formatter));
            m.setFin(LocalDate.parse(mantenimiento.get("fin"),formatter));
            m.setKm_recorridos((Double.parseDouble(mantenimiento.get("kilometros"))));

            mr.save(m);
        }
    }
}
