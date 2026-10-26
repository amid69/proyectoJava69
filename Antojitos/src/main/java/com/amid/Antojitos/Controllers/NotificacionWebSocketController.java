package com.amid.Antojitos.Controllers;

import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class NotificacionWebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

@Autowired
private UsuariosService usuariosService;

    public void enivarNotificacionAlWebSopcket (String mensaje, String usuarioNombre){
        List<UsuariosModel> listaUsuarios = this.usuariosService.buscarUsuariosPorPerfil(1L);

        for(UsuariosModel usu: listaUsuarios){
        simpMessagingTemplate.convertAndSend("/topic/notificaciones-"+ usu.getId(),
                Map.of(

                        "mensaje",mensaje,
                        "usuario", usuarioNombre ,
                        "fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))

                )
                );}
    }



    public void enivarNotificacionDeEstadoAlWebSopcket (String mensaje,Long usuarioId , String estado){

        simpMessagingTemplate.convertAndSend("/topic/pedido-usuario-" + usuarioId,
                Map.of(

                        "mensaje",mensaje,
                         "estado", estado,
                        "fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))

                )
        );
    }

}
