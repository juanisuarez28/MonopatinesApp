package com.example.administracion.service.dto;

import com.example.administracion.model.mysql.Administrador;
import com.example.administracion.model.mysql.Rol;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AdministradorDTO implements Serializable {

        private Long id;

        private String nombre;


        private Rol rol;

        public AdministradorDTO(Long id, String nombre, Rol rol) {
            this.id = id;
            this.nombre = nombre;
            this.rol = rol;
        }

        public AdministradorDTO(Administrador a) {
            this.id = a.getId();
            this.nombre = a.getNombre();
            this.rol = a.getRol();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public Rol getRol() {
            return rol;
        }


}
