package com.amid.Antojitos.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo nombre es obligatorio")
    @Email(message = "Ingrese un correo valido")
    private String correo;
    @NotBlank(message = "El campo contraseña es obligatorio")
    private String password;
}
