package com.amid.Antojitos.Services;

import com.amid.Antojitos.DTOs.ProductoCreacionDto;
import com.amid.Antojitos.Modelos.CategoriaModel;
import com.amid.Antojitos.Modelos.ProductoModel;
import com.amid.Antojitos.Repositorios.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {


    @Autowired
    private IProductoRepository repositorio;

    @Autowired
    private CategoriaService categoriaService;

    public List<ProductoModel> traerProductos(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public ProductoModel traerProducto(Long id){

        Optional<ProductoModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (ProductoCreacionDto dto){

        ProductoModel producto = new ProductoModel();
        producto.setDescripcion(dto.getDescripcion());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        this.repositorio.save(producto);

    }
    public void editar (ProductoModel producto){

        this.repositorio.save(producto);

    }

    public void eliminar (Long id){

        this.repositorio.deleteById(id);
    }

    public List<ProductoModel> traerProductoPorCategoria(Long id){

        CategoriaModel categoriaModel = categoriaService.traerCategoria(id);

        if(categoriaModel !=null){
           List<ProductoModel> listaProductos = categoriaModel.getProductos();
        return listaProductos;
        }
        return null;
    }


}
