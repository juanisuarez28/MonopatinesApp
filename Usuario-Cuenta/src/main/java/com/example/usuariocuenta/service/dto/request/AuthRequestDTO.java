package com.example.usuariocuenta.service.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthRequestDTO {

    @NotNull( message = "El email es obligatorio." )
    @NotEmpty( message = "El email es obligatorio." )
    private String email;

    @NotNull( message = "La contraseña es obligatorio." )
    @NotEmpty( message = "La contraseña es obligatorio." )
    @Size(min = 4, max = 100, message = "La contraseña debe tener un minimo de 4 y un maximo de 100 caracteres.")
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
