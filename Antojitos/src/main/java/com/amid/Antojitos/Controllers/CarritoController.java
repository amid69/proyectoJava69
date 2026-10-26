package com.amid.Antojitos.Controllers;

import com.amid.Antojitos.DTOs.CambiarEstadoCarritoDto;
import com.amid.Antojitos.DTOs.CarritoCreacionDto;
import com.amid.Antojitos.DTOs.CarritoResponseDto;
import com.amid.Antojitos.DTOs.CarritoTraerTodosDto;
import com.amid.Antojitos.JWT.UserInfoDetails;
import com.amid.Antojitos.Modelos.CarritoModel;
import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Services.CarritoService;
import com.amid.Antojitos.Services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private NotificacionWebSocketController notificacionWebSocketController;

    @PostMapping ("/crear-Carrito")
    public ResponseEntity<?> crearCarrito(@RequestBody CarritoCreacionDto carritoCreacionDto){

try {
    UserInfoDetails userDetails =
            (UserInfoDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

    UsuariosModel usuario =
            usuariosService.traer(userDetails.getId());

    carritoService.crearCarrito(carritoCreacionDto,usuario);

    notificacionWebSocketController.enivarNotificacionAlWebSopcket(
            "Nuevo pedido recibido",
            usuario.getNombre());


    return ResponseEntity.status(HttpStatus.OK).body(carritoCreacionDto);


} catch (Exception e) {
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensaje",e.getMessage()));
}
    }



    @GetMapping("/traer-carritos")
    ResponseEntity<?> traerCarritos(){

        UserInfoDetails userDetails =
                (UserInfoDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        UsuariosModel usuario =
                usuariosService.traer(userDetails.getId());

        if(usuario != null){

            List<CarritoResponseDto> listaCarritos = carritoService.traerCarritos(usuario);
            return ResponseEntity.status(HttpStatus.OK).body(listaCarritos);

        }

        return null;

    }


    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/traer-todos-carritos")
    ResponseEntity<?> traerTodosCarritos(){

try{
    UserInfoDetails userDetails =
            (UserInfoDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

    UsuariosModel usuario =
            usuariosService.traer(userDetails.getId());
    List<CarritoTraerTodosDto> listaCarritos = carritoService.traerTodosCarritos(usuario);
    return ResponseEntity.status(HttpStatus.OK).body(listaCarritos);

} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("mensaje", "Intentelo mas trade"));
}

    }

    @PreAuthorize("hasRole('Administrador')")
    @PutMapping ("/editar-estados-carrito")
    ResponseEntity<?> editarEstadoCarrito(@RequestBody CambiarEstadoCarritoDto dto){

        try{

            this.carritoService.cambiarEstadocarrito(dto);
            CarritoModel carrito = this.carritoService.traer(dto.getCarritoId());
            notificacionWebSocketController.enivarNotificacionDeEstadoAlWebSopcket(
                 "Su pedido cambio de estado",
                    carrito.getUsuario().getId(),
                    carrito.getEstados().getNombre()
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Estado modificado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", "Intentelo mas trade"));
        }

    }


}
