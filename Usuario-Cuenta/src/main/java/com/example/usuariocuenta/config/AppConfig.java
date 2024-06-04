package com.example.usuariocuenta.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("clienteRest")
    public RestTemplate registrarRestTemplate(){return new RestTemplate();}

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("UsuarioCuenta")
                .pathsToMatch("/usuario/**","/cuenta/**","/api/**")
                .build();
    }

    @Bean("OpenAPI")
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                                          @Value("${application-version}") String version) {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("UsuarioCuenta API")
                        .version(version)
                        .description(description)
                        .license(new License().name("UsuarioCuenta API Licence")));
    }
}