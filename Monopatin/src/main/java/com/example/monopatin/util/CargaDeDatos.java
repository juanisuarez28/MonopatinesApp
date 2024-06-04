package com.example.monopatin.util;

import com.example.monopatin.model.mysql.Monopatin;
import com.example.monopatin.model.mysql.Pausa;
import com.example.monopatin.model.mysql.Precio;
import com.example.monopatin.model.mysql.Viaje;
import com.example.monopatin.repository.mysql.MonopatinRepositorio;
import com.example.monopatin.repository.mysql.PausaRepositorio;
import com.example.monopatin.repository.mysql.PrecioRepositorio;
import com.example.monopatin.repository.mysql.ViajeRepositorio;
import com.example.monopatin.service.MonopatinServicio;
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

    private final MonopatinRepositorio monopatin;

    private final PausaRepositorio pausa;
    private final PrecioRepositorio precio;

    private final ViajeRepositorio viaje;



    @Autowired
    public CargaDeDatos(MonopatinRepositorio monopatin, PausaRepositorio pausa, PrecioRepositorio precio, ViajeRepositorio viaje, MonopatinServicio ms) {
        this.monopatin = monopatin;
        this.pausa = pausa;
        this.precio = precio;
        this.viaje = viaje;
    }

    public void cargarDatosDesdeCSV() throws IOException{

        CSVParser datosMonopatin = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Monopatin\\src\\main\\java\\com\\example\\monopatin\\csv\\Monopatin.csv"));
        CSVParser datosPausa = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Monopatin\\src\\main\\java\\com\\example\\monopatin\\csv\\Pausa.csv"));
        CSVParser datosPrecio = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Monopatin\\src\\main\\java\\com\\example\\monopatin\\csv\\Precio.csv"));
        CSVParser datosViaje = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Monopatin\\src\\main\\java\\com\\example\\monopatin\\csv\\Viaje.csv"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (CSVRecord monopatin : datosMonopatin){
            Monopatin m = new Monopatin();

            m.setId(Long.valueOf(monopatin.get("id")));
            m.setKilometraje(Double.parseDouble(monopatin.get("kilometraje")));
            m.setEnMantenimiento(Boolean.parseBoolean(monopatin.get("enMantenimiento")));
            m.setNumeroSerie(monopatin.get("numeroSerie"));


            this.monopatin.save(m);
        }

        for (CSVRecord precio : datosPrecio){
            Precio p = new Precio();

            p.setId((Long.valueOf(precio.get("idPrecio"))));
            p.setClave(precio.get("clave"));
            p.setValor(Double.parseDouble(precio.get("valor")));
            p.setFechaFacturacion(LocalDate.parse(precio.get("fechaFacturacion"),formatter));
            p.setValorPorPausaExtendida(Double.valueOf(precio.get("valorPorPausa")));

            this.precio.save(p);
        }

        for (CSVRecord viaje : datosViaje){
            Viaje v = new Viaje();

            v.setId(Long.valueOf(viaje.get("idViaje")));
            v.setFechaInicio(LocalDate.parse(viaje.get("fechaInicio"),formatter));
            v.setFechaFin(LocalDate.parse(viaje.get("fechaFin"),formatter));
            v.setKilometrosRecorridos(Double.parseDouble(viaje.get("kmRecorridos")));

            Long monopatinId = Long.valueOf(viaje.get("monopatinId"));
            v.setMonopatin(this.monopatin.findById(monopatinId).map(Monopatin :: new).orElse(null));

            Long precioId = Long.valueOf(viaje.get("precioId"));
            v.setPrecio(this.precio.findById(precioId).map(Precio :: new).orElse(null));

            this.viaje.save(v);
        }





        for (CSVRecord pausa : datosPausa){
            Pausa p = new Pausa();

            p.setId(Long.valueOf(pausa.get("idPausa")));
            p.setPausaTotal(Long.valueOf(pausa.get("pausaTotal")));
            p.setFechaInicio(LocalDate.parse(pausa.get("fechaInicio"),formatter));
            p.setFechaFin(LocalDate.parse(pausa.get("fechaFin"),formatter));

            Long viajeId = Long.valueOf(pausa.get("viajeId"));
            p.setViaje(this.viaje.findById(viajeId).map(Viaje :: new).orElse(null));

            this.pausa.save(p);
        }





    }
}
