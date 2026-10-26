package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICarritoRepository extends JpaRepository<CarritoModel, Long> {

    List<CarritoModel> findAllByOrderByFechaPedidoDesc();// ← más reciente primero


    List<CarritoModel> findByUsuarioIdOrderByFechaPedidoDesc(Long usuarioId);


}
