package com.example.usuariocuenta.service;


import com.example.usuariocuenta.model.mysql.Usuario;
import com.example.usuariocuenta.repository.mysql.AuthorityRepository;
import com.example.usuariocuenta.repository.mysql.CuentaRepositorio;
import com.example.usuariocuenta.repository.mysql.UsuarioRepositorio;
import com.example.usuariocuenta.service.dto.ParadaDTO;
import com.example.usuariocuenta.service.dto.user.request.UserRequestDTO;
import com.example.usuariocuenta.service.dto.user.response.UserResponseDTO;
import com.example.usuariocuenta.service.exception.EnumUserException;
import com.example.usuariocuenta.service.exception.NotFoundException;
import com.example.usuariocuenta.service.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.usuariocuenta.service.dto.UsuarioDTO;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServicio {

    private final RestTemplate restTemplate;
    private final UsuarioRepositorio userRepository;
    private final CuentaRepositorio accountRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDTO createUser(UserRequestDTO request ) {
        if( this.userRepository.existsUsersByEmailIgnoreCase( request.getEmail() ) )
            throw new UserException( EnumUserException.already_exist, String.format("Ya existe un usuario con email %s", request.getEmail() ) );
        final var accounts = this.accountRepository.findAllById( request.getCuentas() );
        if( accounts.isEmpty() )
            throw new UserException(EnumUserException.invalid_account,String.format("No se encontro ninguna cuenta con id %s", request.getCuentas().toString()));
        final var authorities = request.getAuthorities()
                .stream()
                .map( string -> this.authorityRepository.findById( string ).orElseThrow( () -> new NotFoundException("Autority", string ) ) )
                .toList();
        if( authorities.isEmpty() )
            throw new UserException( EnumUserException.invalid_authorities,
                    String.format("No se encontro ninguna autoridad con id %s", request.getAuthorities().toString() ) );
        final var user = new Usuario( request );
        user.setCuentas( accounts );
        user.setAuthorities( authorities );
        final var encryptedPassword = passwordEncoder.encode( request.getPassword() );
        user.setPassword( encryptedPassword );
        final var createdUser = this.userRepository.save( user );
        return new UserResponseDTO( createdUser );
    }

    @Transactional
    public List<UsuarioDTO> findAll() throws Exception {
        return userRepository.findAll().stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO findById(Long id) throws Exception {
        return userRepository.findById(id).map(UsuarioDTO::new).orElse(null);
    }

    @Transactional
    public ResponseEntity<?> getMonopatinesCercanos(Long idUsuario) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        UsuarioDTO usuario = this.findById(idUsuario);
        if (usuario != null){
            ResponseEntity<?> response2 = restTemplate.exchange("http://localhost:8081/parada/monopatinesCercanos/" + usuario.getX() +"/" + usuario.getY(),
                    HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ParadaDTO>>() {
                    });
            return response2;
        }
        return null;
    }

    @Transactional
    public UsuarioDTO save(Usuario entity) throws Exception {
        userRepository.save(entity);
        return this.findById(entity.getId());
    }

    @Transactional
    public Usuario update(long id, Usuario updatedUsuario) throws ChangeSetPersister.NotFoundException {
        Usuario existingUsuario = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos necesarios
        existingUsuario.setCel(updatedUsuario.getCel());
        existingUsuario.setEmail(updatedUsuario.getEmail());
        existingUsuario.setNombre(updatedUsuario.getNombre());
        existingUsuario.setApellido(updatedUsuario.getApellido());
        existingUsuario.setX(updatedUsuario.getX());
        existingUsuario.setY(updatedUsuario.getY());
        existingUsuario.setPassword(updatedUsuario.getPassword());

        // Guardar la entidad actualizada
        return userRepository.save(existingUsuario);
    }
    @Transactional
    public ResponseEntity<?> delete(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminación exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad con ID " + id + " no existe");
        }
    }

}
