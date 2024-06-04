package com.example.administracion;


import com.example.administracion.model.clases.Monopatin;
import com.example.administracion.service.ServicioAdministracion;
import com.example.administracion.service.dto.EstadoMonopatinResponse;
import com.example.administracion.service.dto.MonopatinDTO;
import com.example.administracion.service.dto.ReporteKilometrajeDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdministracionApplicationTests {

    @InjectMocks
    private ServicioAdministracion servicioAdministracion;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testSettearMonopatinAMantenimiento() {
        Long id = 1L;

        MonopatinDTO monopatinDTO = new MonopatinDTO();
        ResponseEntity<MonopatinDTO> responseEntity = new ResponseEntity<>(monopatinDTO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        MonopatinDTO monopatinEditadoDTO = new MonopatinDTO();
        ResponseEntity<MonopatinDTO> responseEntityEditado = new ResponseEntity<>(monopatinEditadoDTO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntityEditado);

        ResponseEntity result = servicioAdministracion.settearMonopatinAMantenimiento(id);

        assertEquals(HttpStatus.OK, result.getStatusCode(), "La respuesta debería ser HttpStatus.OK");
    }



    //unitario
    @Test
    public void testGetComparacionEstados_Success() {

        EstadoMonopatinResponse estadoMonopatinResponse = new EstadoMonopatinResponse();
        ResponseEntity<EstadoMonopatinResponse> responseEntity = new ResponseEntity<>(estadoMonopatinResponse, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(EstadoMonopatinResponse.class)))
                .thenReturn(responseEntity);

        ResponseEntity<?> result = servicioAdministracion.getComparacionEstados();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    //unitario
    @Test
    public void testGetComparacionEstados_ErrorFromService() {
        // Configuración del test

        // Simular la respuesta del servicio REST con un código de estado diferente de 2xx
        ResponseEntity<EstadoMonopatinResponse> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(EstadoMonopatinResponse.class)))
                .thenReturn(responseEntity);

        ResponseEntity<?> result = servicioAdministracion.getComparacionEstados();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }

    //unitario
    @Test
    public void testGetTotalFacturadoEntreMeses() {
        // Configuración del test
        int anio = 2023;
        int mesInicio = 1;
        int mesFin = 3;

        // Simular la respuesta del servicio REST
        int totalFacturado = 1000;
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(totalFacturado, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        // Ejecutar el método que se está probando
        ResponseEntity<?> result = servicioAdministracion.getTotalFacturadoEntreMeses(anio, mesInicio, mesFin);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(totalFacturado, result.getBody());
    }

    @Test
    public void testGetMonopatinesPorAnio_Success() {
        // Configuración del test
        Integer cantViajes = 5;
        Integer anio = 2023;

        // Simular la respuesta del servicio REST
        List<Monopatin> monopatines = Arrays.asList(new Monopatin(), new Monopatin()); // Ajusta según tus necesidades
        ResponseEntity<List<Monopatin>> responseEntity = new ResponseEntity<>(monopatines, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<Monopatin>>() {})))
                .thenReturn(responseEntity);

        // Ejecutar el método que se está probando
        ResponseEntity<?> result = servicioAdministracion.getMonopatinesPorAnio(cantViajes, anio);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(monopatines, result.getBody());
    }

    @Test
    public void testGetKilometraje_Success() {
        // Configuración del test
        Long umbral = 100L;
        boolean incluirPausas = true;

        // Simular la respuesta del servicio REST
        List<ReporteKilometrajeDTO> reportes = Arrays.asList(new ReporteKilometrajeDTO(), new ReporteKilometrajeDTO()); // Ajusta según tus necesidades
        ResponseEntity<List<ReporteKilometrajeDTO>> responseEntity = new ResponseEntity<>(reportes, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<ReporteKilometrajeDTO>>() {})))
                .thenReturn(responseEntity);

        // Ejecutar el método que se está probando
        ResponseEntity<?> result = servicioAdministracion.getKilometraje(umbral, incluirPausas);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reportes, result.getBody());
    }





}






