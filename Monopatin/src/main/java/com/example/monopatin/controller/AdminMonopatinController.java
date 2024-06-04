package com.example.monopatin.controller;

import com.example.monopatin.model.mysql.Monopatin;
import com.example.monopatin.model.mysql.Viaje;
import com.example.monopatin.security.AuthorityConstants;
import com.example.monopatin.service.MonopatinServicio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/monopatin")
public class AdminMonopatinController {
    private MonopatinServicio ms;
    private RestTemplate restTemplate;


    @Autowired
    public AdminMonopatinController(MonopatinServicio ms, RestTemplate restTemplate){
        this.ms=ms;
        this.restTemplate = restTemplate;
    }


    @Operation(summary = "Obtiene un monopatin x.")
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.findById(id));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }
    @Operation(summary = "Obtiene todos los monopatines.")
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.findAll());
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }
    @Operation(summary = "Edita un monopatin x.")
    @PutMapping("/editar/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Monopatin entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.update(id,entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo editar, o no se encontró el ID. Revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Obtiene un viaje x.")
    @GetMapping("/viaje/{viajeId}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<Viaje> obtenerViajePorId(@PathVariable Long viajeId) {
        Viaje viaje = restTemplate.getForObject("http://viajes-service/viajes/{viajeId}", Viaje.class, viajeId);
        return ResponseEntity.ok(viaje);
    }

    @Operation(summary = "Da de alta un nuevo monopatin.")
    @PostMapping("/agregar")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> agregarMonopatin(@RequestBody Monopatin m){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo agregar, revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Da de baja un monopatin x.")
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo eliminar, asegurate que el monopatin no tenga una referencia.\"}");
        }
    }

    @Operation(summary = "Obtiene un reporte de los monopatines con mas de x viajes en un año y.")
    @GetMapping ("/viajesPorAnio/{cantViajes}/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getMonopatinPorAnio(@PathVariable Integer cantViajes, @PathVariable Integer anio){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.getMonopatinPorAnio(cantViajes, anio));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");        }
    }

    @Operation(summary = "Obtiene un reporte con los monopatines en estado de mantenimiento y en operacion.")
    @GetMapping("/comparacionEstados")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getComparacionEstados(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.getComparacionEstados());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }










}

