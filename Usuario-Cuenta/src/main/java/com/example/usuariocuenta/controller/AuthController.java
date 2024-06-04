package com.example.usuariocuenta.controller;

import com.example.usuariocuenta.security.AuthorityConstants;
import com.example.usuariocuenta.security.jwt.JWTFilter;
import com.example.usuariocuenta.security.jwt.TokenProvider;
import com.example.usuariocuenta.service.UsuarioServicio;
import com.example.usuariocuenta.service.dto.request.AuthRequestDTO;
import com.example.usuariocuenta.service.dto.user.request.UserRequestDTO;
import com.example.usuariocuenta.service.dto.user.response.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {


    private final UsuarioServicio se;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    /*@Autowired
    public AuthController(UsuarioServicio se, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.se=se;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }*/

    @Operation(summary = "Valida el token de autenticacion")
    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenDTO> validateGet() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        var authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(
                ValidateTokenDTO.builder()
                        .username( user.getName() )
                        .authorities( authorities )
                        .isAuthenticated( true )
                        .build()
        );
    }
    @Data
    @Builder
    public static class ValidateTokenDTO {
        private boolean isAuthenticated;
        private String username;
        private List<String> authorities;
    }

   // INICIAR SESION

    @Operation(summary = "Autentica las credenciales")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate( @Valid @RequestBody AuthRequestDTO request ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword() );

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final var jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Registra un nuevo usuario")
    @PostMapping("/register")
    //@PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request ){
        final var newUser = this.se.createUser( request );
        return new ResponseEntity<>( newUser, HttpStatus.CREATED );
    }

    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
