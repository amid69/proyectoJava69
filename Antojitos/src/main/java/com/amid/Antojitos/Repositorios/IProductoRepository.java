package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<ProductoModel, Long> {
}
