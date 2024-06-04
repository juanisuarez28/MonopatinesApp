package com.example.monopatin.service;

import com.example.monopatin.model.mysql.Monopatin;
import com.example.monopatin.repository.mysql.MonopatinRepositorio;
import com.example.monopatin.service.dto.EstadoMonopatinResponse;
import com.example.monopatin.service.dto.MonopatinDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonopatinServicio {
    private MonopatinRepositorio mr;

    @Autowired
    public MonopatinServicio(MonopatinRepositorio mr){
        this.mr=mr;
    }
    @Transactional
    public MonopatinDTO findById(Long id) {
        return mr.findById(id).map(MonopatinDTO::new).orElse(null);
    }

    @Transactional
    public List<MonopatinDTO> findAll() throws Exception {
        return mr.findAll().stream().map(MonopatinDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public MonopatinDTO save(Monopatin entity) throws Exception {
        mr.save(entity);
        return this.findById(entity.getId());
    }

    @Transactional
    public Monopatin update(Long id, Monopatin updatedMonopatin) {
        // Busca el Monopatin existente por ID
        Monopatin existingMonopatin = mr.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Monopatin con ID " + id + " no encontrado"));

        // Aplica las modificaciones en la entidad existente
        existingMonopatin.setNumeroSerie(updatedMonopatin.getNumeroSerie());
        existingMonopatin.setKilometraje(updatedMonopatin.getKilometraje());
        existingMonopatin.setEnMantenimiento(updatedMonopatin.isEnMantenimiento());
        // Otros cambios necesarios

        // Guarda la entidad actualizada
        Monopatin updatedEntity = mr.save(existingMonopatin);

        // Devuelve la entidad actualizada
        return updatedEntity;
    }
    @Transactional
    public ResponseEntity<String> delete(Long id) throws Exception {
        if (mr.existsById(id)) {
            mr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }
    @Transactional
    public List<MonopatinDTO> getMonopatinPorAnio(Integer cantViajes, Integer anio) {
        return mr.getMonopatinPorAnio(cantViajes, anio).stream().map(MonopatinDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public EstadoMonopatinResponse getComparacionEstados() {
        return mr.getComparacionEstados();
    }
}
