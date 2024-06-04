package com.example.mantenimiento.service;

import com.example.mantenimiento.model.mongo.Mantenimiento;
import com.example.mantenimiento.repository.mongo.MantenimientoRepositorioMongodb;
import com.example.mantenimiento.service.dto.MantenimientoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MantenimientoServicio {
    private MantenimientoRepositorioMongodb mr;

    @Autowired
    public MantenimientoServicio(MantenimientoRepositorioMongodb mr){
        this.mr=mr;
    }

    @Transactional
    public MantenimientoDTO findById(String id) throws Exception {
        return mr.findById(id).map(MantenimientoDTO::new).orElse(null);
    }
    @Transactional
    public List<MantenimientoDTO> findAll() throws Exception {

        return mr.findAll().stream().map(MantenimientoDTO::new).collect(Collectors.toList());
        
    }

    @Transactional
    public MantenimientoDTO save(Mantenimiento entity) throws Exception {
        Mantenimiento nuevoMantenimiento = mr.save(entity);
        return this.findById(nuevoMantenimiento.getId());
    }

    @Transactional
    public Mantenimiento update(String id, Mantenimiento updatedMantenimiento) {
        // Busca el Mantenimiento existente por ID
        Mantenimiento existingMantenimiento = mr.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mantenimiento con ID " + id + " no encontrado"));

        // Aplica las modificaciones en la entidad existente
        existingMantenimiento.setId_monopatin(updatedMantenimiento.getId_monopatin());
        existingMantenimiento.setInicio(updatedMantenimiento.getInicio());
        existingMantenimiento.setFin(updatedMantenimiento.getFin());
        existingMantenimiento.setKm_recorridos(updatedMantenimiento.getKm_recorridos());
        // Otros cambios necesarios

        // Guarda la entidad actualizada
        Mantenimiento updatedEntity = mr.save(existingMantenimiento);

        // Devuelve la entidad actualizada
        return updatedEntity;
    }


    @Transactional
    public ResponseEntity<String> delete(String id) throws Exception {
        if (mr.existsById(id)) {
            mr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

    @Transactional
    public MantenimientoDTO findByIdMonopatin(String idMonopatin) {
        return mr.findByIdMonopatin(idMonopatin).map(MantenimientoDTO::new).orElse(null);
    }
}
