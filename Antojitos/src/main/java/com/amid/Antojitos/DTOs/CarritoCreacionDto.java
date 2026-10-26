package com.amid.Antojitos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoCreacionDto {

    private String observacionGeneral;
    List<ProductosDtoRequest> listaProductos;

}
