package com.amid.Antojitos.Services;

import com.amid.Antojitos.DTOs.*;
import com.amid.Antojitos.Modelos.*;
import com.amid.Antojitos.Repositorios.ICarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private ICarritoRepository repositorio;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoCarritoService productoCarritoService;
    @   Autowired
    private EstadosService estadosService;

    @Transactional
    public void crearCarrito(CarritoCreacionDto carritoCreacionDto, UsuariosModel usuario){

        CarritoModel carrito = new CarritoModel();
        carrito.setFechaPedido(new Date());
        carrito.setEstados(estadosService.traerEstado(4L));
        carrito.setDetallesGenerales(carritoCreacionDto.getObservacionGeneral());

        carrito = this.repositorio.save(carrito); // ← 1. GUARDA el carrito PRIMERO (ya tiene id)

        List<ProductoCarritoModel> listaProductosCarrito = new ArrayList<>();
        List<ProductosDtoRequest> listaProductosDto = carritoCreacionDto.getListaProductos();
        Double total = 0.0;

        for(ProductosDtoRequest productoDto : listaProductosDto){

            ProductoModel productoPrecio = productoService.traerProducto(productoDto.getIdProducto());

            if(productoPrecio != null && productoDto.getCantidad() > 0){

                ProductoCarritoModel producto = new ProductoCarritoModel();
                producto.setCantidad(productoDto.getCantidad());
                producto.setObservaciones(productoDto.getObservacion());
                producto.setNombre(productoPrecio.getNombre());
                producto.setProducto(productoPrecio);
                producto.setCarrito(carrito); // ← 2. ahora sí, carrito YA tiene id

                this.productoCarritoService.guardar(producto); // ← 3. ya funciona
                listaProductosCarrito.add(producto);

                total += productoPrecio.getPrecio() * producto.getCantidad();
            }
        }

        if(!listaProductosCarrito.isEmpty()){
            carrito.setProductos(listaProductosCarrito);
            carrito.setTotal(total);
            carrito.setUsuario(usuario);
            this.repositorio.save(carrito); // ← 4. actualiza el carrito con el total y la lista
        }
    }

    @Transactional
    public List<CarritoResponseDto> traerCarritos(UsuariosModel usuario){

        List<CarritoModel> traerCarritos = this.repositorio.findByUsuarioIdOrderByFechaPedidoDesc(usuario.getId());
        List<CarritoResponseDto> listCarritos = new ArrayList<>();
/*
        private String observacionGeneral;
        List<ProductoMostrarDto> listaProductos;
        private Date fechaPedido;
        private Double total;

            private Long idProducto;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String observacion;
        */
        if(traerCarritos != null){
            for(CarritoModel carrito : traerCarritos){

                CarritoResponseDto carritoDto = new CarritoResponseDto();
                carritoDto.setObservacionGeneral(carrito.getDetallesGenerales());
                carritoDto.setFechaPedido(carrito.getFechaPedido());
                Double total = 0.0;
                List<ProductoMostrarDto> listProductos = new ArrayList<>();

                for(ProductoCarritoModel productoInicial: carrito.getProductos()){
                    ProductoMostrarDto productoDto = new ProductoMostrarDto();
                    productoDto.setCantidad(productoInicial.getCantidad());
                    productoDto.setNombre(productoInicial.getNombre());
                    productoDto.setObservacion(productoInicial.getObservaciones());
                    productoDto.setIdProducto(productoInicial.getProducto().getId());
                    Double totalProducto= productoInicial.getProducto().getPrecio()*
                           productoInicial.getCantidad();
                    productoDto.setPrecio(totalProducto);
                    total += totalProducto;
                    listProductos.add(productoDto);
                }

                carritoDto.setListaProductos(listProductos);
                carritoDto.setTotal(total);
                listCarritos.add(carritoDto);
            }
            return listCarritos;
        }

        return null;

    }

    @Transactional
    public List<CarritoTraerTodosDto> traerTodosCarritos(UsuariosModel usuario){

        List<CarritoModel> traerCarritos = this.repositorio.findAll(Sort.by("id").descending());
        List<CarritoTraerTodosDto> listCarritos = new ArrayList<>();
/*
        private String observacionGeneral;
        List<ProductoMostrarDto> listaProductos;
        private Date fechaPedido;
        private Double total;

            private Long idProducto;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String observacion;
        */
        if(usuario.getUnPerfil().getId() == 1L){
            for(CarritoModel carrito : traerCarritos){

                CarritoTraerTodosDto carritoDto = new CarritoTraerTodosDto();
                carritoDto.setObservacionGeneral(carrito.getDetallesGenerales());
                carritoDto.setFechaPedido(carrito.getFechaPedido());
                carritoDto.setEstado(carrito.getEstados().getNombre());
                carritoDto.setNombreUsuario(carrito.getUsuario().getNombre());
                carritoDto.setId(carrito.getId());
                Double total = 0.0;
                List<ProductoMostrarDto> listProductos = new ArrayList<>();

                for(ProductoCarritoModel productoInicial: carrito.getProductos()){
                    ProductoMostrarDto productoDto = new ProductoMostrarDto();
                    productoDto.setCantidad(productoInicial.getCantidad());
                    productoDto.setNombre(productoInicial.getNombre());
                    productoDto.setObservacion(productoInicial.getObservaciones());
                    productoDto.setIdProducto(productoInicial.getProducto().getId());
                    Double totalProducto= productoInicial.getProducto().getPrecio()*
                            productoInicial.getCantidad();
                    productoDto.setPrecio(totalProducto);
                    total += totalProducto;
                    listProductos.add(productoDto);
                }

                carritoDto.setListaProductos(listProductos);
                carritoDto.setTotal(total);
                listCarritos.add(carritoDto);
            }
            return listCarritos;
        }

        return null;

    }

    public CarritoModel traer(Long id){

        Optional<CarritoModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }


    public void cambiarEstadocarrito(CambiarEstadoCarritoDto dto){

        CarritoModel carritoModel = this.traer(dto.getCarritoId());

        if(carritoModel != null ){
            if(dto.getEstadoId() == 3 || dto.getEstadoId()== 4 || dto.getEstadoId() ==5){
                carritoModel.setEstados(this.estadosService.traerEstado(dto.getEstadoId()));
           this.repositorio.save(carritoModel);
            }
        }

    }



}
