package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Long> {

}
