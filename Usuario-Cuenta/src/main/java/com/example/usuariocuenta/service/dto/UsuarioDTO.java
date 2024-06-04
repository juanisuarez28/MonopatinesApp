package com.example.usuariocuenta.service.dto;

import com.example.usuariocuenta.model.mysql.Cuenta;
import com.example.usuariocuenta.model.mysql.Usuario;
import lombok.Getter;

import java.util.Set;

@Getter
public class UsuarioDTO {

    private long id;
    private String cel;
    private String email;
    private String nombre;
    private String apellido;
    private double x;
    private double y;
    private String password;

    private Set<Cuenta> cuenta;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.cel = usuario.getCel();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.x = usuario.getX();
        this.y = usuario.getY();
        this.cuenta = usuario.getCuentas();
        this.password=usuario.getPassword();
    }

    public UsuarioDTO(long id, String cel, String email, String nombre, String apellido, double x, double y, Set<Cuenta> cuenta, String password) {
        this.id = id;
        this.cel = cel;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.x = x;
        this.y = y;
        this.cuenta = cuenta;
        this.password=password;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }




    public long getId() {
        return id;
    }


    public String getCel() {
        return cel;
    }


    public String getEmail() {
        return this.email;
    }


    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public Set<Cuenta> getCuenta() {
        return cuenta;
    }

    public String getPassword() {
        return password;
    }
}