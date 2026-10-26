package com.amid.Antojitos.JWT;


import com.amid.Antojitos.Modelos.UsuariosModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String password;
    private String email;
    private String nombre;
    private Long id;
    private Long estadoId;
    private List<GrantedAuthority> authorities;
    public UserInfoDetails(UsuariosModel userInfo){
        nombre= userInfo.getNombre();
        password = userInfo.getPassword();
        email= userInfo.getCorreo();
        id = userInfo.getId();
        estadoId = userInfo.getUnEstado().getId();
        authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userInfo.getUnPerfil().getNombre()));
    }

    public Long getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    }

    @Override
    public boolean isEnabled() {
        return estadoId == 1;
     }
}
