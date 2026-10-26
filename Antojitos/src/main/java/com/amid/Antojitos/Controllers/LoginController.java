package com.amid.Antojitos.Controllers;

import com.amid.Antojitos.DTOs.JwtResponseDto;
import com.amid.Antojitos.DTOs.LoginDto;
import com.amid.Antojitos.JWT.JwtService;
import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){

        UsuariosModel usuario = this.usuariosService.buscarPorCorreo(dto.getCorreo());

        if(usuario == null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String,String>(){{
                put("mensaje","No hay algun usuario registrado con el correo ingresado");}});

        }
        else {

            if(this.passwordEncoder.matches(dto.getPassword(), usuario.getPassword())){
                if(usuario.getUnEstado().getId() == 1){
                    String token = jwtService.generateToken(usuario.getId());
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new JwtResponseDto(usuario.getId(),usuario.getNombre(),usuario.getUnPerfil().getNombre(),
                                    usuario.getUnPerfil().getId(),token));
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String,String>(){{
                    put("mensaje","Cuenta inhabilitada");}});
            }else
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String,String>(){{
                    put("mensaje","Contraseña incorrecta");}});

            }

        }
    }


}
