package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarEstadoCarritoDto {

    private Long estadoId;
    private Long carritoId;

}
