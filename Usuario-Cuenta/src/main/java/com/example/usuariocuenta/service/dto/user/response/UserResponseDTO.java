package com.example.usuariocuenta.service.dto.user.response;

import com.example.usuariocuenta.model.mysql.Usuario;
import lombok.Data;

@Data
public class UserResponseDTO {

    private final long id;
    private final String nombre;
    private final String apellido;
    private final String email;

    public UserResponseDTO( Usuario user ){
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.email = user.getEmail();
    }

}
