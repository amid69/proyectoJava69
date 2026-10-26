package com.amid.Antojitos.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name ="Carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaPedido;
    private String detallesGenerales;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_Id")
    private UsuariosModel usuario;

    @ManyToOne
    @JoinColumn(name = "estados_id", nullable = true)
    private EstadosModel estados;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoCarritoModel> productos;

}
