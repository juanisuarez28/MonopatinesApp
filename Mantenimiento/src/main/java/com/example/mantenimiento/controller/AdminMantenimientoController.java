package com.example.mantenimiento.controller;

import com.example.mantenimiento.model.mongo.Mantenimiento;
import com.example.mantenimiento.security.AuthorityConstants;
import com.example.mantenimiento.service.MantenimientoServicio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimiento")
public class AdminMantenimientoController {

    private MantenimientoServicio ms;

    @Autowired
    public AdminMantenimientoController(MantenimientoServicio ms) {
        this.ms = ms;
    }

    @Operation(summary = "Crea un nuevo mantenimiento.")
    @PostMapping("/agregar")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> agregarMantenimiento(@RequestBody Mantenimiento m) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.save(m));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Edita un mantenimiento x.")
    @PutMapping("/editar/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> editarMantenimiento(@PathVariable String id, @RequestBody Mantenimiento m) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.update(id, m));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo editar, o no se encontró el ID. Revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Obtiene un mantenimiento por un monopatin x.")
    @GetMapping("/getMonopatin/{idMonopatin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getMantenimientoPorIdMonopatin(@PathVariable String idMonopatin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.findByIdMonopatin(idMonopatin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Obtiene todos los mantenimientos.")
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ms.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }


    @Operation(summary = "Obtiene un mantenimiento x.")
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getOne(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.findById(id));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

    @Operation(summary = "Elimina un mantenimiento x.")
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ms.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. no se pudo eliminar intente nuevamente.\"}");
        }
    }

}
