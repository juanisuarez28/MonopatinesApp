package com.example.mantenimiento.repository.mysql;

import com.example.mantenimiento.model.mysql.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MantenimientoRepositorio extends JpaRepository<Mantenimiento,Long> {
    @Query("SELECT m FROM Mantenimiento m WHERE m.id_monopatin = :idMonopatin AND m.fin = null")
    Optional<Mantenimiento> findByIdMonopatin(Long idMonopatin);
}
