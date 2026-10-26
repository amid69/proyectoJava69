package com.amid.Antojitos.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCreacionDto {

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;
    @NotBlank(message = "El campo descrpcion es obligatorio")
    private String descripcion;



}
