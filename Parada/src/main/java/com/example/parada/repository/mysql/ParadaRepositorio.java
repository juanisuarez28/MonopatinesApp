package com.example.parada.repository.mysql;

import com.example.parada.model.mysql.Parada;
import com.example.parada.service.dto.ParadaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepositorio extends JpaRepository<Parada,Long> {

    @Query("SELECT p FROM Parada p WHERE SQRT((p.x - :usuarioX) * (p.x - :usuarioX) + (p.y - :usuarioY) * (p.y - :usuarioY)) <= :umbral ORDER BY SQRT((p.x - :usuarioX) * (p.x - :usuarioX) + (p.y - :usuarioY) * (p.y - :usuarioY)) limit 5")
    List<ParadaDTO> getMonopatinesCercanos(double usuarioX, double usuarioY, double umbral);
}
