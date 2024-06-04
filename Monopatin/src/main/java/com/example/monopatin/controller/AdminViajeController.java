package com.example.monopatin.controller;

import com.example.monopatin.model.mysql.Viaje;
import com.example.monopatin.security.AuthorityConstants;
import com.example.monopatin.service.ViajeServicio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viajes")
public class AdminViajeController {
    private ViajeServicio rs;

    @Autowired
    public AdminViajeController(ViajeServicio rs){
        this.rs = rs;

    }

    @Operation(summary = "Obtiene todos los viajes.")
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @Operation(summary = "Obtiene un viaje x.")
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.findById(id));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

    @Operation(summary = "Da de alta un nuevo viaje.")
    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> save(@RequestBody Viaje entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.save(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Edita un viaje x.")
    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Viaje entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.update(id,entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo editar, o no se encontró el ID. Revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Da de baja un viaje x.")
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. no se pudo eliminar intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Obtiene un reporte con los kilometrajes mayores a x y con pausas dependiendo de y.")
    @GetMapping("/getReporteKilometraje/{umbral}/conPausas/{incluirPausa}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getReporteKilometraje(@PathVariable Long umbral, @PathVariable boolean incluirPausa){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rs.getReporteKilometraje(umbral, incluirPausa));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");

        }
    }

    @Operation(summary = "Obtiene un reporte de lo facturado en un año x, entre un mes z y un mes y.")
    @GetMapping("/facturado/{anio}/{mesInicio}/{mesFin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getFacturadoEntreMeses(@PathVariable int anio, @PathVariable int mesInicio, @PathVariable int mesFin){

        try{
            return ResponseEntity.status(HttpStatus.OK).body(rs.getFacturadoEntreMeses(anio, mesInicio, mesFin));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
}
