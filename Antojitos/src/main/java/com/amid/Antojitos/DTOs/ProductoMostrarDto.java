package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMostrarDto {

    private Long idProducto;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String observacion;


}
