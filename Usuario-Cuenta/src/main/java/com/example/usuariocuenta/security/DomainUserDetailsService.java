package com.example.usuariocuenta.security;
import com.example.usuariocuenta.model.mysql.Authority;
import com.example.usuariocuenta.model.mysql.Usuario;
import com.example.usuariocuenta.repository.mysql.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findUserByEmailIgnoreCase( email )
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario con email " + email ));
    }


    private org.springframework.security.core.userdetails.User createSpringSecurityUser(Usuario user) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}