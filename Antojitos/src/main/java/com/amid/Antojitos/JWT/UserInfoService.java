package com.amid.Antojitos.JWT;

import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Repositorios.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UsuariosModel usuario = usuariosRepository
                .findByIdWithPerfil(Long.parseLong((id)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserInfoDetails(usuario);
    }
}
