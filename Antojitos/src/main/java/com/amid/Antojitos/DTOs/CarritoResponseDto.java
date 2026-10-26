package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponseDto {

    private String observacionGeneral;
    List<ProductoMostrarDto> listaProductos;
    private Date fechaPedido;
    private Double total;

}
