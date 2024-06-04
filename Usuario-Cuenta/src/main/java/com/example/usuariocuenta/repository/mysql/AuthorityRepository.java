package com.example.usuariocuenta.repository.mysql;


import com.example.usuariocuenta.model.mysql.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
