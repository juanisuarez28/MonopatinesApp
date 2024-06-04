package com.example.monopatin.repository.mysql;

import com.example.monopatin.model.mysql.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PausaRepositorio extends JpaRepository<Pausa, Long> {
}
