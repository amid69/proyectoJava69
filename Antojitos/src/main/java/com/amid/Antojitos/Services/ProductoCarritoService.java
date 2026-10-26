package com.amid.Antojitos.Services;

import com.amid.Antojitos.Modelos.ProductoCarritoModel;
import com.amid.Antojitos.Modelos.ProductoModel;
import com.amid.Antojitos.Repositorios.IProductoCarritoRepository;
import com.amid.Antojitos.Repositorios.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoCarritoService {


    @Autowired
    private IProductoCarritoRepository repositorio;

    public List<ProductoCarritoModel> traerProductosCarrito(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public ProductoCarritoModel traerProductoCarritos(Long id){

        Optional<ProductoCarritoModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (ProductoCarritoModel modelo){

        this.repositorio.save(modelo);

    }

}
