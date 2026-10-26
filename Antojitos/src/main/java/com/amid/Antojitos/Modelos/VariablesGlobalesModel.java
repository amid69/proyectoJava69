package com.amid.Antojitos.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="Variables_Globales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariablesGlobalesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String valor;

}
