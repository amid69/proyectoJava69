package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.PerfilModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilRepository extends JpaRepository<PerfilModel, Long> {
}
