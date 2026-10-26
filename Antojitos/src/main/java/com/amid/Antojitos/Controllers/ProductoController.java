package com.amid.Antojitos.Controllers;

import com.amid.Antojitos.DTOs.ProductoCreacionDto;
import com.amid.Antojitos.Modelos.ProductoModel;
import com.amid.Antojitos.Services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crear-producto")
    public ResponseEntity<?> crearProducto (@Valid @RequestBody ProductoCreacionDto dto){
        try{
            this.productoService.guardar(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>(){{
                put("mensaje","Creado con exito");
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>(){{
                put("mensaje","Intentelo mas tarde");
            }});
        }
    }

    @GetMapping ("/traer-producto/{id}")
    public ResponseEntity<?> traerProducto (@PathVariable ("id") Long id) {
        try {
            ProductoModel producto = this.productoService.traerProducto(id);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
                    put("mensaje", "Intentelo mas tarde");
                }});
            } else {
                return ResponseEntity.status((HttpStatus.OK)).body(producto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
                put("mensaje", "Intentelo mas tarde");
        }});
        }
    }

    @GetMapping ("/traer-productos")
    public ResponseEntity<?> traerProductos (){
        return ResponseEntity.status((HttpStatus.OK)).body(this.productoService.traerProductos());
    }

    @PutMapping ("/editar-producto/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable ("id") Long id,
                                            @Valid @RequestBody ProductoCreacionDto dto){
       try{
        ProductoModel productoModel = this.productoService.traerProducto(id);
        if(productoModel != null){
            productoModel.setPrecio(dto.getPrecio());
            productoModel.setDescripcion(dto.getDescripcion());
            productoModel.setNombre(dto.getNombre());
            productoService.editar(productoModel);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {{
                put("mensaje", "Editado correctamente");
            }});
        }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
               put("mensaje", "Intentelo mas tarde");
           }});
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
               put("mensaje", "Intentelo mas tarde");
           }});
       }

    }


@GetMapping("/traer-productos-categoria/{id}")
    public ResponseEntity<?> traerProductosPorCategoria(@PathVariable ("id") Long id){

        try{

            List<ProductoModel> listaProductos = this.productoService.traerProductoPorCategoria(id);
            return ResponseEntity.status((HttpStatus.OK)).body(listaProductos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
                put("mensaje", "Intentelo mas tarde");
            }});
        }

}




    }