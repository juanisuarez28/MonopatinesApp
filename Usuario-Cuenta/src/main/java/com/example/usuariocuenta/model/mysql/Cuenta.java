package com.example.usuariocuenta.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate date;
    @Column
    private boolean anulada;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.LAZY, mappedBy = "cuentas" )
    private Set<Usuario> usuarios;

    public Cuenta(Long id, LocalDate date) {
        this.id = id;
        this.date = date;
        this.anulada = false;
    }

    public Cuenta(Long id, LocalDate date, boolean anulada) {
        this.id = id;
        this.date = date;
        this.anulada = anulada;
    }

    public Cuenta(){
        this.anulada = false;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
