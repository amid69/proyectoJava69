package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CambiarEstadoUsuarioDto {

    private Long idUsuario;
    private Long idEstado;

}
