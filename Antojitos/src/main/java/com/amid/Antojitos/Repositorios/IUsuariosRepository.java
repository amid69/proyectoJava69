package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.EstadosModel;
import com.amid.Antojitos.Modelos.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuariosRepository extends JpaRepository<UsuariosModel, Long> {
    @Query("SELECT u FROM UsuariosModel u JOIN FETCH u.unPerfil WHERE u.id = :id")
    Optional<UsuariosModel> findByIdWithPerfil(@Param("id") Long id);

    UsuariosModel findByCorreo(String correo);
    Optional<UsuariosModel> findByCorreoAndUnEstado(String correo, EstadosModel estados);
    UsuariosModel findByNombre(String nombre);

    List<UsuariosModel> findByUnPerfilId(Long perfilId);

}
