package com.example.usuariocuenta.service;

import com.example.usuariocuenta.model.mysql.Cuenta;
import com.example.usuariocuenta.repository.mysql.CuentaRepositorio;
import com.example.usuariocuenta.service.dto.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServicio {
    private CuentaRepositorio cr;
    private RestTemplate restTemplate;

    @Autowired
    public CuentaServicio(CuentaRepositorio cr, RestTemplate restTemplate){
        this.cr=cr;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public CuentaDTO findById(Long id) {
        return cr.findById(id).map(CuentaDTO::new).orElse(null);
    }

    @Transactional
    public List<CuentaDTO> findAll() throws Exception {
        return cr.findAll().stream().map(CuentaDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public CuentaDTO save(Cuenta entity) throws Exception {
        cr.save(entity);
        return this.findById(entity.getId().longValue());
    }

    @Transactional
    public CuentaDTO update(Long id, Cuenta entity) throws Exception {
        return this.save(entity);
    }
    @Transactional
    public ResponseEntity<?> delete(Long id) throws Exception {
        if (cr.existsById(id)) {
            cr.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

    @Transactional
    public CuentaDTO anularCuenta(Long id) throws Exception {
        CuentaDTO response = this.findById(id);
        Cuenta cuenta = new Cuenta(response.getId(), response.getDate(), true);
        return  this.save(cuenta);
    }

    @Transactional
    public CuentaDTO habilitarCuenta(Long id) throws Exception {
        CuentaDTO response = this.findById(id);
        Cuenta cuenta = new Cuenta(response.getId(), response.getDate());
        return this.save(cuenta);
    }
}
