package com.example.apigateway.router;

import com.example.apigateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter authFilter ) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/authenticate" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8085"))
                .route("auth-service-register", r -> r.path("/api/register" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8085"))
                .route("mantenimiento", r -> r.path("/mantenimiento/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8083"))
                .route("administrar", r -> r.path("/administrar/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8084"))
                .route("monopatin", r -> r.path("/monopatin/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8089"))
                .route("pausa", r -> r.path("/pausa/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8089"))
                .route("precios", r -> r.path("/precios/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8089"))
                .route("viajes", r -> r.path("/viajes/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8089"))
                .route("parada", r -> r.path("/parada/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8081"))
                .route("usuario", r -> r.path("/usuario/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8085"))
                .route("cuenta", r -> r.path("/cuenta/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8085"))
                .route("cuenta", r -> r.path("/viajes/getReporteKilometraje/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8089"))
                .route("swagger_mantenimiento", r -> r.path("/swagger-mantenimiento/**")
                        .uri("http://localhost:8083/swagger-ui.html"))
                .route("swagger_administrar", r -> r.path("/swagger-administrar/**")
                        .uri("http://localhost:8084/swagger-ui.html"))
                .route("swagger_usuariocuenta", r -> r.path("/swagger-usuariocuenta/**")
                        .uri("http://localhost:8085/swagger-ui.html"))
                .route("swagger_monopatin", r -> r.path("/swagger-monopatin/**")
                        .uri("http://localhost:8089/swagger-ui.html"))
                .route("swagger_parada", r -> r.path("/swagger-parada/**")
                        .uri("http://localhost:8081/swagger-ui.html"))
                .route("swagger_apigateway", r -> r.path("/swagger-apigateway/**")
                        .uri("http://localhost:8088/swagger-ui.html"))
                .build();
    }

}
