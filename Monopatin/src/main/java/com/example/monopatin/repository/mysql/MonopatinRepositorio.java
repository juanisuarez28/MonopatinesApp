package com.example.monopatin.repository.mysql;

import com.example.monopatin.model.mysql.Monopatin;
import com.example.monopatin.service.dto.EstadoMonopatinResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepositorio extends JpaRepository<Monopatin,Long> {

    @Query("SELECT m.id, COUNT(v) " +
            "FROM Monopatin m " +
            "JOIN m.viajes v " +
            "WHERE FUNCTION('YEAR', v.fechaInicio) = :anio " +
            "GROUP BY m.id " +
            "HAVING COUNT(v) > :cantViajes")
    List<Monopatin>getMonopatinPorAnio(Integer cantViajes, Integer anio);

    @Query("SELECT " +
            "new com.example.monopatin.service.dto.EstadoMonopatinResponse(" +
            "SUM(CASE WHEN m.enMantenimiento = false THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN m.enMantenimiento = true THEN 1 ELSE 0 END)) " +
            "FROM Monopatin m")
    EstadoMonopatinResponse getComparacionEstados();
}

