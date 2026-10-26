package com.amid.Antojitos.Repositorios;

import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.ProductoCarritoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoCarritoRepository extends JpaRepository<ProductoCarritoModel, Long> {
}
