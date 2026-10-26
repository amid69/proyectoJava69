package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.EstadosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEstadosRepository extends JpaRepository<EstadosModel, Long> {
    public List<EstadosModel> findByIdIn (List<Long> id);
}
