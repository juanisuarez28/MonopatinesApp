package com.example.monopatin.repository.mysql;

import com.example.monopatin.model.mysql.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrecioRepositorio extends JpaRepository<Precio, Long> {

    @Query("SELECT p FROM Precio p WHERE p.fechaFacturacion  <= CURDATE() ORDER BY p.fechaFacturacion DESC LIMIT 1")
    public Optional<Precio> getCurrentPrice();

    @Query("SELECT p FROM Precio p WHERE p.fechaFacturacion <= :fecha ORDER BY p.fechaFacturacion DESC LIMIT 1")
    public Optional<Precio> getPriceByDate(LocalDate fecha);
}
