package com.example.apigateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class AppConfig {

    @Bean("clienteRest")
    public RestTemplate registrarRestTemplate(){return new RestTemplate();}

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("gateway")
                .pathsToMatch("/**")
                .build();
    }

    @Bean("OpenAPI")
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                                          @Value("${application-version}") String version) {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("Api-gateway API")
                        .version(version)
                        .description(description)
                        .license(new License().name("Api-gateway API Licence")));
    }
}