package com.amid.Antojitos.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponseDto {
private Long id;
private String nombre;
private String perfil;
private Long perfilId;
private String token;



}
