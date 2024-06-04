package com.example.usuariocuenta.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.usuariocuenta.model.mysql.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUserByEmailIgnoreCase(String email);

    boolean existsUsersByEmailIgnoreCase(String email );
}
