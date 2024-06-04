package com.example.usuariocuenta.model.mysql;


import com.example.usuariocuenta.service.dto.user.request.UserRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String cel;
    @Column( nullable = false )
    private String email;
    @Column( nullable = false )
    private String nombre;
    @Column( nullable = false )
    private String apellido;

    @Column(nullable = false)
    private String password;
    @Column
    private double x;

    @Column
    private double y;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "rel_user__account",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Cuenta> cuentas;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "rel_user__authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    public Usuario(Usuario u) {
        this.id = u.getId();
        this.cel = u.getCel();
        this.email = u.getEmail();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.x = u.getX();
        this.y = u.getY();
        this.cuentas = u.getCuentas();
        this.authorities = u.getAuthorities();
        this.password = u.getPassword();
    }

    public Usuario(long id, String cel, String email, String nombre, String apellido, double x, double y, Set<Cuenta> cuentas, Set<Authority> authorities, String password) {
        this.id = id;
        this.cel = cel;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.x = x;
        this.y = y;
        this.cuentas = cuentas;
        this.authorities = authorities;
        this.password = password;

    }

    public Usuario(UserRequestDTO request) {
        this.nombre = request.getNombre();
        this.apellido = request.getApellido();
        this.email = request.getEmail();
    }

    public void setCuentas( Collection<Cuenta> cuentas ){
        this.cuentas = new HashSet<>( cuentas );
    }

    public void setAuthorities( Collection<Authority> authorities ){
        this.authorities = new HashSet<>( authorities );
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Usuario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }


    public Set<Authority> getAuthorities() {
        return authorities;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {
        return this.password;
    }
}
