package com.example.usuariocuenta.repository.mysql;

import com.example.usuariocuenta.model.mysql.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {
}
