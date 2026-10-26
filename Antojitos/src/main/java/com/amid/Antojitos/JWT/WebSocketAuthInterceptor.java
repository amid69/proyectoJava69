package com.amid.Antojitos.JWT;

import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Services.UsuariosService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class WebSocketAuthInterceptor extends OncePerRequestFilter {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Solo procesa WebSocket
        if (request.getRequestURI().contains("/ws-")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (jwtService.validateToken(token)) {
                    // Extrae el userId del token
                    String userId = jwtService.extractUserId(token);

                    // ← CAMBIA ESTO: trae el usuario de la BD
                    UsuariosModel usuario = usuariosService.traer(Long.parseLong(userId));

                    if (usuario != null) {
                        // Usa tu constructor completo
                        UserInfoDetails userDetails = new UserInfoDetails(usuario);

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println("✅ WebSocket: Usuario autenticado - " + usuario.getNombre());
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }


}
