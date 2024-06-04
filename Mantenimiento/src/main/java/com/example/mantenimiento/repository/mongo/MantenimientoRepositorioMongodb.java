package com.example.mantenimiento.repository.mongo;

import com.example.mantenimiento.model.mongo.Mantenimiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface MantenimientoRepositorioMongodb extends MongoRepository<Mantenimiento, String> {

    @Query("{ 'id_monopatin': ?0, 'fin': null }")
    Optional<Mantenimiento> findByIdMonopatin(String idMonopatin);

}
