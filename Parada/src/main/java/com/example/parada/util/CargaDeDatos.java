package com.example.parada.util;

import com.example.parada.model.mysql.Parada;
import com.example.parada.repository.mysql.ParadaRepositorio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class CargaDeDatos {

    private final ParadaRepositorio pr;


    @Autowired
    public CargaDeDatos(ParadaRepositorio pr) {
        this.pr = pr;
    }

    public void cargarDatosDesdeCSV() throws IOException{

        CSVParser datosParadas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Parada\\src\\main\\java\\com\\example\\parada\\csv\\Parada.csv"));

        for (CSVRecord parada : datosParadas){
            Parada p = new Parada();

            p.setId(Long.parseLong(parada.get("idParada")));
            p.setX(Long.valueOf(parada.get("x")));
            p.setY(Long.valueOf(parada.get("y")));

            pr.save(p);
        }
    }
}
