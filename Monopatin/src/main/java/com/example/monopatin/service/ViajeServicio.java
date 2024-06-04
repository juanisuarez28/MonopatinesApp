package com.example.monopatin.service;

import com.example.monopatin.model.mysql.Viaje;
import com.example.monopatin.repository.mysql.ViajeRepositorio;
import com.example.monopatin.service.dto.ReporteKilometrajeDTO;
import com.example.monopatin.service.dto.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViajeServicio {
    private ViajeRepositorio rr;

    @Autowired
    public ViajeServicio(ViajeRepositorio rr) {
        this.rr = rr;
    }

    @Transactional
    public ViajeDTO findById(Long id) {
        return rr.findById(id).map(ViajeDTO::new).orElse(null);
    }

    @Transactional
    public List<ViajeDTO> findAll() throws Exception {
        return rr.findAll().stream().map(ViajeDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ViajeDTO save(Viaje entity) throws Exception {
        rr.save(entity);
        return this.findById(entity.getId());
    }

    @Transactional
    public Viaje update(Long id, Viaje updatedViaje) throws ChangeSetPersister.NotFoundException {
        Viaje existingViaje = rr.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos necesarios
        existingViaje.setMonopatin(updatedViaje.getMonopatin());
        existingViaje.setFechaInicio(updatedViaje.getFechaInicio());
        existingViaje.setFechaFin(updatedViaje.getFechaFin());
        existingViaje.setKilometrosRecorridos(updatedViaje.getKilometrosRecorridos());
        existingViaje.setPausas(updatedViaje.getPausas());
        existingViaje.setPrecio(updatedViaje.getPrecio());

        // Guardar la entidad actualizada
        return rr.save(existingViaje);
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) throws Exception {
        if (rr.existsById(id)) {
            rr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

    @Transactional
    public List<ReporteKilometrajeDTO> getReporteKilometraje(long umbral, boolean conPausas){
        if(conPausas){
            return rr.getReporteKilometrajeConPausas(umbral);
        }else{
            return rr.getReporteKilometraje(umbral);
        }
    }

    @Transactional
    public Integer getFacturadoEntreMeses(int anio, int mesInicio, int mesFin) {
        return rr.getFacturadoEntreMeses(anio, mesInicio, mesFin);
    }
}
