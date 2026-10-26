package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDtoRequest {

    private Long idProducto;
    private String nombre;
    //El precio consultarlo desde la BD
    private Integer cantidad;
    private String observacion;

}
