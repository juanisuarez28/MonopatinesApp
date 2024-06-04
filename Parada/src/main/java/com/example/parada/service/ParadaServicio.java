package com.example.parada.service;

import com.example.parada.model.mysql.Parada;
import com.example.parada.repository.mysql.ParadaRepositorio;
import com.example.parada.service.dto.ParadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParadaServicio {
    private ParadaRepositorio pr;
    private double umbral = 1000000000000000000.0;

    @Autowired
    public ParadaServicio(ParadaRepositorio pr) {
        this.pr = pr;
    }

    @Transactional
    public ParadaDTO findById(Long id) {
        return pr.findById(id).map(ParadaDTO::new).orElse(null);
    }

    @Transactional
    public List<ParadaDTO> findAll() throws Exception {
        return pr.findAll().stream().map(ParadaDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ParadaDTO save(Parada entity) throws Exception {
        pr.save(entity);
        return this.findById(entity.getId());
    }

    @Transactional
    public Parada update(long id, Parada updatedParada) throws ChangeSetPersister.NotFoundException {
        Parada existingParada = pr.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos necesarios
        existingParada.setX(updatedParada.getX());
        existingParada.setY(updatedParada.getY());
        existingParada.setMonopatinesEnLaParada(updatedParada.getMonopatinesEnLaParada());

        // Guardar la entidad actualizada
        return pr.save(existingParada);
    }
    @Transactional
    public ResponseEntity<String> delete(Long id) {
        if (pr.existsById(id)) {
            pr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

    @Transactional
    public List<ParadaDTO> getMonopatinesCercanos(double x, double y) {
        return this.pr.getMonopatinesCercanos(x, y, this.umbral);
    }
}
