package com.example.usuariocuenta.util;

import com.example.usuariocuenta.model.mysql.Authority;
import com.example.usuariocuenta.model.mysql.Cuenta;
import com.example.usuariocuenta.model.mysql.Usuario;
import com.example.usuariocuenta.repository.mysql.AuthorityRepository;
import com.example.usuariocuenta.repository.mysql.CuentaRepositorio;
import com.example.usuariocuenta.repository.mysql.UsuarioRepositorio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
public class CargaDeDatos {

    private final CuentaRepositorio cr;
    private final UsuarioRepositorio ur;

    private final AuthorityRepository ar;

    @Autowired
    public CargaDeDatos(CuentaRepositorio cr, UsuarioRepositorio ur, AuthorityRepository ar) {
        this.cr = cr;
        this.ur = ur;
        this.ar = ar;
    }

    public void cargarDatosDesdeCSV() throws IOException{

        CSVParser datosUsuarios = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Usuario-Cuenta\\src\\main\\java\\com\\example\\usuariocuenta\\csv\\Usuario.csv"));
        CSVParser datosCuentas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Usuario-Cuenta\\src\\main\\java\\com\\example\\usuariocuenta\\csv\\Cuenta.csv"));
        CSVParser datosAuthority = CSVFormat.DEFAULT.withHeader().parse(new FileReader("C:\\Users\\stran\\Desktop\\TPE_2\\TPE (2)\\TPE\\Usuario-Cuenta\\src\\main\\java\\com\\example\\usuariocuenta\\csv\\Authority.csv"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (CSVRecord cuenta : datosCuentas){
            Cuenta c = new Cuenta();

            c.setId(Long.valueOf(cuenta.get("idCuenta")));
            c.setAnulada(Boolean.parseBoolean(cuenta.get("anulada")));
            c.setDate(LocalDate.parse(cuenta.get("date"),formatter));

            Long usuarioId = Long.valueOf(cuenta.get("usuarioId"));
            Set<Usuario> usuariosSet = new HashSet<>();
            usuariosSet.add(this.ur.findById(usuarioId).map(Usuario::new).orElse(null));
            c.setUsuarios(usuariosSet);

            cr.save(c);
        }

        for(CSVRecord usuario: datosUsuarios){
            Usuario u = new Usuario();

            u.setId(Long.parseLong(usuario.get("idUsuario")));
            u.setX(Double.parseDouble(usuario.get("x")));
            u.setY(Double.parseDouble(usuario.get("y")));
            u.setCel(usuario.get("cel"));
            u.setApellido(usuario.get("apellido"));
            u.setNombre(usuario.get("nombre"));
            u.setEmail(usuario.get("email"));
            u.setPassword(usuario.get("password"));
            ur.save(u);
        }

        for (CSVRecord authority : datosAuthority){
            Authority a = new Authority();

            a.setName(authority.get("name"));

            ar.save(a);
        }



    }
}
