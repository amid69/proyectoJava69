package com.amid.Antojitos.Controllers;

import com.amid.Antojitos.DTOs.CambiarEstadoUsuarioDto;
import com.amid.Antojitos.DTOs.CambniarContraDto;
import com.amid.Antojitos.DTOs.LoginDto;
import com.amid.Antojitos.JWT.UserInfoDetails;
import com.amid.Antojitos.Modelos.EstadosModel;
import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Services.EstadosService;
import com.amid.Antojitos.Services.PerfilService;
import com.amid.Antojitos.Services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EstadosService estadosService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuariosService usuariosService;


    /* ===============================
       SOLO ADMINISTRADOR
       =============================== */

   @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/usuarios")
    public ResponseEntity<?> traerUsuarios() {
        return ResponseEntity.ok(usuariosService.traerUsuariosDTO());
    }

    //@PreAuthorize("hasRole('Administrador')")
    @PostMapping("/auth/usuarios")
    public ResponseEntity<?> crearUsuarios(@Valid @RequestBody LoginDto dto) {

        UsuariosModel existente = usuariosService.buscarPorCorreo(dto.getCorreo());

        if (existente != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", "Intentelo con otro correo"));
        }

        UsuariosModel nuevo = new UsuariosModel(
                dto.getNombre(),
                dto.getCorreo(),
                passwordEncoder.encode(dto.getPassword()),
                "",
                new Date(),
                perfilService.traer(2L), // usuario normal
                estadosService.traerEstado(1L) // activo
        );

        usuariosService.guardar(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Creado correctamente"));
    }


    @PreAuthorize("hasRole('Administrador')")
    @PutMapping("/usuarios-estados")
    public ResponseEntity<?> cambiarEstadosUsuario(
            @RequestBody CambiarEstadoUsuarioDto dto) {

        UsuariosModel usuarioModificar =
                usuariosService.traer(dto.getIdUsuario());

        EstadosModel estadoNuevo =
                estadosService.traerEstado(dto.getIdEstado());

        if (usuarioModificar == null || estadoNuevo == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", "Datos inválidos"));
        }

        if (estadoNuevo.getId() != 1 && estadoNuevo.getId() != 2) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", "Estado no permitido"));
        }

        usuarioModificar.setUnEstado(estadoNuevo);
        usuariosService.guardar(usuarioModificar);

        return ResponseEntity.ok(
                Map.of("mensaje", "Se cambio el estado correctamente"));
    }

    /* ===============================
       CUALQUIER USUARIO AUTENTICADO
       =============================== */

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/editarUsuario-contraseña")
    public ResponseEntity<?> editarUsuario(
            @RequestBody CambniarContraDto dto) {


        UserInfoDetails userDetails =
                (UserInfoDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        UsuariosModel usuario =
                usuariosService.traer(userDetails.getId());

        if (!passwordEncoder.matches(
                dto.getContraseniaAntigua(),
                usuario.getPassword())) {

            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje",
                            "La contraseña antigua no coincide"));
        }

        if (!dto.getContraseniaNueva()
                .equals(dto.getContraseniaNueva2())) {

            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje",
                            "Las contraseñas nuevas no coinciden"));
        }

        usuario.setPassword(
                passwordEncoder.encode(dto.getContraseniaNueva()));

        usuariosService.guardar(usuario);

        return ResponseEntity.ok(
                Map.of("mensaje",
                        "Se modifico correctamente la Contraseña"));
    }

    /*
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/usuarios-editar")
    public ResponseEntity<?> editarUsuariosNombreEmail(
            @RequestBody UsuarioEmailNombreRequest dto) {

        UserInfoDetails userDetails =
                (UserInfoDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        UsuariosModel usuario =
                usuariosService.traer(userDetails.getId());

        if (dto.getNombre() != null &&
                !dto.getNombre().isBlank()) {

            usuario.setNombre(dto.getNombre());
            usuariosService.guardar(usuario);

            return ResponseEntity.ok(
                    Map.of("mensaje",
                            "Se cambio correctamente el nombre"));
        }

        if (dto.getEmail() != null &&
                !dto.getEmail().isBlank() &&
                usuariosService.buscarPorCorreo(dto.getEmail()) == null) {

            usuario.setCorreo(dto.getEmail());
            usuariosService.guardar(usuario);

            return ResponseEntity.ok(
                    Map.of("mensaje",
                            "Se cambio correctamente el Email"));
        }

        return ResponseEntity.badRequest()
                .body(Map.of("mensaje", "Datos inválidos"));
    }*/
}
