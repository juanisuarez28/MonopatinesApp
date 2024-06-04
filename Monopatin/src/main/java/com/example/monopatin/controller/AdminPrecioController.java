package com.example.monopatin.controller;

import com.example.monopatin.model.mysql.Precio;
import com.example.monopatin.security.AuthorityConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.monopatin.service.PrecioServicio;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RestController
@RequestMapping("/precios")
public class AdminPrecioController {
    
    private RestTemplate restTemplate;
    private PrecioServicio sp;

    @Autowired
    public AdminPrecioController(PrecioServicio sp, RestTemplate restTemplate) {
        this.sp = sp;
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Da de alta un nuevo precio.")
    @PostMapping("/agregar")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> agregarPrecio(@RequestBody Precio p){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sp.save(p));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }
    @Operation(summary = "Obtiene todos los precios.")
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sp.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @Operation(summary = "Obtiene un precio x.")
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sp.findById(id));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

    @Operation(summary = "Edita un precio x.")
    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Precio entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sp.update(id,entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo editar, o no se encontró el ID. Revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Da de baja un precio x.")
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sp.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. no se pudo eliminar. Asegurese que el objeto a eliminar no tenga una relacion.\"}");
        }
    }

    @Operation(summary = "encuentra los precios en vigencia")
    @GetMapping("/vigentes")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getCurrentPrice(){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sp.findByCurrentPrice());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }
    @Operation(summary = "encuentra los precios vigentes a partir de cierta fecha")
    @GetMapping("/vigentes/{fecha}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getPriceByDate(@PathVariable LocalDate fecha){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sp.findByPriceByDate(fecha));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

}
