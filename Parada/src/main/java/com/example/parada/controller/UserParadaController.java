package com.example.parada.controller;

import com.example.parada.security.AuthorityConstants;
import com.example.parada.service.ParadaServicio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/parada")
public class UserParadaController {
    private ParadaServicio ps;

    @Autowired
    public UserParadaController(ParadaServicio ps){
        this.ps=ps;
    }

    @Operation(summary = "Obtiene una lista de paradas cercanas a la posicion X e Y.")
    @GetMapping("/monopatinesCercanos/{x}/{y}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> getMonopatinesCercanos(@PathVariable double x, @PathVariable double y){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ps.getMonopatinesCercanos(x,y));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");

        }
    }
}
