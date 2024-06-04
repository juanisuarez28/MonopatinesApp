package com.example.administracion.service;

import com.example.administracion.model.mysql.Administrador;
import com.example.administracion.model.clases.*;
import com.example.administracion.repository.mysql.AdministracionRepo;
import com.example.administracion.security.jwt.TokenPropagatingInterceptor;
import com.example.administracion.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioAdministracion {
    private RestTemplate restTemplate;
    private AdministracionRepo ar;

    @Autowired
    public ServicioAdministracion(AdministracionRepo ar, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.getInterceptors().add(new TokenPropagatingInterceptor());
        this.ar = ar;
    }

    @Transactional
    public ResponseEntity settearMonopatinAMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<MonopatinDTO> response = restTemplate.exchange("http://localhost:8089/monopatin/" + id,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<MonopatinDTO>(){});
        if (response.getStatusCode().is2xxSuccessful()) {
            MonopatinDTO monopatin = response.getBody();
            Monopatin m = new Monopatin(monopatin);
            if (!monopatin.isEnMantenimiento()) {
                this.agregarMantenimiento(m);
                m.setEnMantenimiento(true);
                HttpEntity<Monopatin> requestEntity2 = new HttpEntity<>(m, headers);
                ResponseEntity<MonopatinDTO> response2 = restTemplate.exchange("http://localhost:8089/monopatin/editar/" + id,
                        HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<MonopatinDTO>() {
                        });
                return response2;
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El monopatin ya está en mantenimiento");

            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatin a dar de baja");
    }

    @Transactional
    public ResponseEntity anularCuenta(Long idCuenta) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<CuentaDTO> response = restTemplate.exchange("http://localhost:8085/cuenta/" + idCuenta,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<CuentaDTO>() {
                });
        if (response.getStatusCode().is2xxSuccessful()) {
            CuentaDTO cuenta = response.getBody();
            Cuenta c = new Cuenta(cuenta);
            if (!c.isAnulada()) {
                c.setAnulada(true);
                HttpEntity<Cuenta> requestEntity2 = new HttpEntity<>(c, headers);
                ResponseEntity<CuentaDTO> response2 = restTemplate.exchange("http://localhost:8085/cuenta/anular/" + idCuenta,
                        HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<CuentaDTO>() {
                        });
                return response2;
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Esta cuenta ya está anulada");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta a anular");
    }

    @Transactional

    public ResponseEntity finMantenimientoMonopatin(Long id) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Monopatin> response = restTemplate.exchange("http://localhost:8089/monopatin/" + id,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Monopatin>() {
                });
        if (response.getStatusCode().is2xxSuccessful()) {
            Monopatin monopatin = response.getBody();
            if (monopatin.isEnMantenimiento()) {
                this.finMantenimiento(id);
                monopatin.setEnMantenimiento(false);
                HttpEntity<Monopatin> requestEntity2 = new HttpEntity<>(monopatin, headers);
                ResponseEntity<Monopatin> response2 = restTemplate.exchange("http://localhost:8089/monopatin/editar/" + id,
                        HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Monopatin>() {
                        });
                return response2;
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este monopatin ya está en funcionamiento");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatin en mantenimiento a dar de alta");
    }

    @Transactional
    public void finMantenimiento(Long idMonopatin) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Mantenimiento> response = restTemplate.exchange("http://localhost:8083/mantenimiento/getMonopatin/" + idMonopatin,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Mantenimiento>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            Mantenimiento m = response.getBody();
            Long idMantenimento = m.getId();
            m.setFin(LocalDate.now());
            HttpEntity<Mantenimiento> requestEntity2 = new HttpEntity<>(m, headers);
            ResponseEntity<Mantenimiento> response2 = restTemplate.exchange("http://localhost:8083/monopatin/editar/" + idMantenimento,
                    HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Mantenimiento>() {
                    });
        }
    }

    @Transactional
    public ResponseEntity<?> agregarMantenimiento(Monopatin monopatin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);



        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId_monopatin(monopatin.getId());
        mantenimiento.setInicio(LocalDate.now());
        mantenimiento.setFin(null);
        mantenimiento.setKm_recorridos(monopatin.getKilometraje());

        HttpEntity<Mantenimiento> requestEntity = new HttpEntity<>(mantenimiento, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8083/mantenimiento/agregar",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }

    @Transactional

    public ResponseEntity agregarMonopatin(Monopatin m) {
        HttpHeaders headers = new HttpHeaders();

        Monopatin monopatinNuevo = new Monopatin(m);
        HttpEntity<Monopatin> requestEntity = new HttpEntity<>(monopatinNuevo, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8089/monopatin/agregar",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }

    @Transactional

    public ResponseEntity deleteMonopatin(Long idMonopatin) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Monopatin> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8089/monopatin/eliminar/" + idMonopatin,
                HttpMethod.DELETE, requestEntity, String.class);
        if (response.hasBody()) {
            return ResponseEntity.ok("Monopatin eliminado con exito");//200 ok
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monopatin a eliminar no encontrado");
    }

    @Transactional

    public ResponseEntity agregarParada(Parada p) {
        HttpHeaders headers = new HttpHeaders();

        Parada paradaNueva = new Parada(p);
        HttpEntity<Parada> requestEntity = new HttpEntity<>(paradaNueva, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/parada/agregar",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }

    @Transactional

    public ResponseEntity deleteParada(Long idParada) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Parada> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/parada/eliminar/" + idParada,
                HttpMethod.DELETE, requestEntity, String.class);
        if (response.hasBody()) {
            return ResponseEntity.ok("Parada eliminada con exito");//200 ok
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parada a eliminar no encontrada");
    }



    @Transactional
    public ResponseEntity habilitarCuenta(Long idCuenta) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Cuenta> response = restTemplate.exchange("http://localhost:8085/cuenta/" + idCuenta,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Cuenta>() {
                });
        if (response.getStatusCode().is2xxSuccessful()) {
            Cuenta cuenta = response.getBody();
            if (cuenta.isAnulada()) {
                cuenta.setAnulada(false);
                HttpEntity<Cuenta> requestEntity2 = new HttpEntity<>(cuenta, headers);
                ResponseEntity<Cuenta> response2 = restTemplate.exchange("http://localhost:8085/cuenta/habilitar/" + idCuenta,
                        HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Cuenta>() {
                        });
                return response2;
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Esta cuenta ya está habilitada");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta a habilitar");
    }

    @Transactional
    public List<AdministradorDTO> findAll() throws Exception {
        return ar.findAll().stream().map(AdministradorDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public AdministradorDTO findById(Long id) throws Exception {
        return ar.findById(id).map(AdministradorDTO::new).orElse(null);
    }

    @Transactional
    public AdministradorDTO save(Administrador entity) throws Exception {
        ar.save(entity);
        return this.findById(entity.getId().longValue());
    }

    @Transactional
    public Administrador update(Long id, Administrador updatedAdministrador) throws Exception {
        Optional<Administrador> optionalAdministrador = ar.findById(id);

        if (optionalAdministrador.isPresent()) {
            Administrador existingAdministrador = optionalAdministrador.get();

            existingAdministrador.setNombre(updatedAdministrador.getNombre());
            existingAdministrador.setRol(updatedAdministrador.getRol());

            ar.save(existingAdministrador);

            return existingAdministrador;
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) throws Exception {
        if (ar.existsById(id)) {
            ar.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

    @Transactional
    public ResponseEntity<?> getKilometraje(Long umbral, boolean incluirPausas) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            return restTemplate.exchange("http://localhost:8089/viajes/getReporteKilometraje/" + umbral +"/conPausas/" + incluirPausas,
                    HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ReporteKilometrajeDTO>>() {
                    });

    }

    @Transactional
    public ResponseEntity<?> getMonopatinesPorAnio(Integer cantViajes, Integer anio) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange("http://localhost:8089/monopatin/viajesPorAnio/" + cantViajes + "/" + anio,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Monopatin>>() {
                });


    }

    @Transactional
    public ResponseEntity<?> getTotalFacturadoEntreMeses(int anio, int mesInicio, int mesFin) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange("http://localhost:8088/viajes/facturado/" + anio + "/" + mesInicio + "/" + mesFin,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Integer>() {
                });

        return response;
    }

    @Transactional
    public ResponseEntity<?> getComparacionEstados(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange("http://localhost:8089/monopatin/comparacionEstados",
                HttpMethod.GET, requestEntity, EstadoMonopatinResponse.class
                );

    }




}


