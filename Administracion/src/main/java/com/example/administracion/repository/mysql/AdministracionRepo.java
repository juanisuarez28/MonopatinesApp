package com.example.administracion.repository.mysql;

import com.example.administracion.model.mysql.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministracionRepo extends JpaRepository<Administrador, Long> {
}
