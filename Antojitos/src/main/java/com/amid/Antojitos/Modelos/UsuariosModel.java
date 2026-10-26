package com.amid.Antojitos.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name ="Usuarios")
@Getter // Cambia @Data por @Getter y @Setter para mayor control
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"unPerfil", "unEstado"}) // EVITA EL BUCLE INFINITO
@EqualsAndHashCode(exclude = {"unPerfil", "unEstado"})
public class UsuariosModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo nombre es obligatorio")
    @Email(message = "Ingrese un correo obligatorio")
    private String correo;
    @NotBlank(message = "El campo contraseña es obligatorio")
    private String password;
    private String token;
    private Date fecha;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_Id")
    private PerfilModel unPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estados_Id")
    private EstadosModel unEstado;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoModel> listaCarritos;

    public UsuariosModel(String nombre, String correo, String password, String token, Date fecha, PerfilModel unPerfil, EstadosModel unEstado) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.token = token;
        this.fecha = fecha;
        this.unPerfil = unPerfil;
        this.unEstado = unEstado;
    }

}
