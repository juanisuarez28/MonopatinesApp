package com.example.monopatin.repository.mysql;


import com.example.monopatin.model.mysql.Viaje;
import com.example.monopatin.service.dto.ReporteKilometrajeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepositorio extends JpaRepository<Viaje,Long> {

    @Query("select NEW com.example.monopatin.service.dto.ReporteKilometrajeDTO(v.monopatin.id,sum(v.kilometrosRecorridos)) from Viaje v where v.kilometrosRecorridos >= :umbral group by v.monopatin.id")
    public List<ReporteKilometrajeDTO> getReporteKilometraje(long umbral);


    @Query("select NEW com.example.monopatin.service.dto.ReporteKilometrajeDTO(v.monopatin.id,sum(v.kilometrosRecorridos),sum(p.pausaTotal)) from Viaje v join Pausa p on v.id=p.id where v.kilometrosRecorridos >= :umbral group by v.monopatin.id")
    public List<ReporteKilometrajeDTO> getReporteKilometrajeConPausas(long umbral);

    @Query("SELECT SUM(p.valor + p.valorPorPausaExtendida) FROM Viaje v JOIN v.precio p WHERE FUNCTION('YEAR', v.fechaInicio) = :anio AND FUNCTION('MONTH', v.fechaInicio) BETWEEN :mesInicio AND :mesFin")
    Integer getFacturadoEntreMeses(int anio, int mesInicio, int mesFin);
}
