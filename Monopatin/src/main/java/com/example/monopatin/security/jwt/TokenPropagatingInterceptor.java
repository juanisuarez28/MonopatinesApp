package com.example.monopatin.security.jwt;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TokenPropagatingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Obtener el token de donde sea que lo tengas almacenado
        String token = obtainToken();

        // Agregar el token al encabezado de la solicitud
        request.getHeaders().add("Authorization", "Bearer " + token);

        // Continuar con la ejecución de la solicitud
        return execution.execute(request, body);
    }

    private String obtainToken() {
        // Implementar la lógica para obtener el token
        // Puede ser de un proveedor de seguridad, un contexto de seguridad, etc.
        return "your_token_here";
    }
}
