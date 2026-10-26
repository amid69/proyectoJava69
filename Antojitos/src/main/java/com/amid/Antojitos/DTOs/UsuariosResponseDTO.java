package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private Long unPerfil;
    private Long unEstado;
    private String estado;



}
