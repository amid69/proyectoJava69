package com.amid.Antojitos.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="productoCarrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCarritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String observaciones;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_Id")
    private ProductoModel producto;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoModel carrito;

    public String getNombre (){
        return producto.getNombre();
    }

}
