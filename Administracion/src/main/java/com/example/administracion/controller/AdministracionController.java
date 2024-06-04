package com.example.administracion.controller;

import com.example.administracion.model.mysql.Administrador;
import com.example.administracion.model.clases.Monopatin;
import com.example.administracion.model.clases.Parada;
import com.example.administracion.security.AuthorityConstants;
import com.example.administracion.service.ServicioAdministracion;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/administrar")
@RequiredArgsConstructor
public class AdministracionController {
    private ServicioAdministracion sa;

    @Autowired
    public AdministracionController(ServicioAdministracion sa) {
        this.sa = sa;
    }

    @Operation(summary = "Setea un monopatin x a en mantenimiento.")
    @PutMapping("/monopatines/setearAMantenimiento/{idMonopatin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> setearAMantenimiento (@PathVariable Long idMonopatin){
        return ResponseEntity.status(HttpStatus.OK).body(sa.settearMonopatinAMantenimiento(idMonopatin));
    }

    @Operation(summary = "Setea un monopatin x a en operacion.")
    @PutMapping("/monopatines/finAMantenimiento/{idMonopatin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> finAMantenimiento (@PathVariable Long idMonopatin){
        return ResponseEntity.status(HttpStatus.OK).body(sa.finMantenimientoMonopatin(idMonopatin));
    }

    @Operation(summary = "Crea un nuevo monopatin.")
    @PostMapping("/monopatines")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> agregarMonopatin(@RequestBody Monopatin m){
        return ResponseEntity.status(HttpStatus.OK).body(sa.agregarMonopatin(m));
    }

    @Operation(summary = "Elimina un monopatin x.")
    @DeleteMapping("/monopatines/delete/{idMonopatin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> deleteMonopatin (@PathVariable Long idMonopatin){
        return ResponseEntity.status(HttpStatus.OK).body(sa.deleteMonopatin(idMonopatin));
    }

    @Operation(summary = "Dade alta una nueva parada.")
    @PostMapping("/paradas")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> agregarParada(@RequestBody Parada p){
        return ResponseEntity.status(HttpStatus.OK).body(sa.agregarParada(p));
    }

    @Operation(summary = "Elimina una parada x.")
    @DeleteMapping("/paradas/delete/{idParada}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<ResponseEntity> deleteParada (@PathVariable Long idParada){
        return ResponseEntity.status(HttpStatus.OK).body(sa.deleteMonopatin(idParada));
    }


    @Operation(summary = "Obtiene todos los administradores.")
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @Operation(summary = "Obtiene un administrador x.")
    @GetMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.findById(id));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encuentra el objeto buscado" +
                    ".\"}");
        }
    }

    @Operation(summary = "Da de alta un nuevo administrador.")
    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> save(@RequestBody Administrador entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.save(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Edita un administrador x.")
    @PutMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Administrador entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.update(id,entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo editar, o no se encontró el ID. Revise los campos e intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Da de baja un administrador x.")
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. no se pudo eliminar intente nuevamente.\"}");
        }
    }

    @Operation(summary = "Obtiene un reporte con los monopatines que tienen más de x kilometraje.")
    @GetMapping("/monopatines/reporte/kilometraje/{limite}/{incluirPausas}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getKilometraje(@PathVariable Long limite, @PathVariable boolean incluirPausas){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(sa.getKilometraje(limite, incluirPausas));
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. no se pudo conseguir lo buscado intente nuevamente.\"}");

        }

    }
    @Operation(summary = "Anula una cuenta temporalmente.")
    @PutMapping("/cuentas/anular/{idCuenta}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> anularCuenta (@PathVariable Long idCuenta){
        return ResponseEntity.status(HttpStatus.OK).body(sa.anularCuenta(idCuenta));
    }

    @Operation(summary = "Habilita una cuenta.")
    @PutMapping("/cuentas/habilitar/{idCuenta}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> habilitarCuenta (@PathVariable Long idCuenta){
        return ResponseEntity.status(HttpStatus.OK).body(sa.habilitarCuenta(idCuenta));
    }

    @Operation(summary = "Obtiene un reporte con los monopatines con más de x viajes en un año y.")
    @GetMapping("/monopatines/viajesPorAnio/{cantViajes}/{anio}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getMonopatinesPorAnio(@PathVariable Integer cantViajes, @PathVariable Integer anio){
            return ResponseEntity.status(HttpStatus.OK).body(sa.getMonopatinesPorAnio(cantViajes, anio));
    }

    @Operation(summary = "Obtiene el total facturado en un año x entre un mes z y un mes y.")
    @GetMapping("/viajes/totalFacturado/{anio}/{mesInicio}/{mesFin}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getTotalFacturadoEntreMeses(@PathVariable int anio, @PathVariable int mesInicio, @PathVariable int mesFin){
        return ResponseEntity.status(HttpStatus.OK).body(sa.getTotalFacturadoEntreMeses(anio, mesInicio, mesFin));
    }

    @Operation(summary = "Obtiene un reporte con los monopatines que estan en mantenimiento y los que estan en operacion.")
    @GetMapping("/monopatines/getComparacionEstados")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> getComparacionEstados(){
        return ResponseEntity.status(HttpStatus.OK).body(sa.getComparacionEstados());
    }





}
