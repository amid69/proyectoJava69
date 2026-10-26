package com.amid.Antojitos.Services;

import com.amid.Antojitos.DTOs.UsuariosResponseDTO;
import com.amid.Antojitos.Modelos.UsuariosModel;
import com.amid.Antojitos.Repositorios.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private IUsuariosRepository repositorio;

    @Autowired
    private EstadosService estadosService;

    public List<UsuariosModel> listar(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public UsuariosModel traer(Long id){

        Optional<UsuariosModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (UsuariosModel modelo){

        this.repositorio.save(modelo);

    }

    public void eliminar (Long id){

        this.repositorio.deleteById(id);
    }

    public UsuariosModel buscarPorCorreoActivo(String correo){

        Optional<UsuariosModel> optional = this.repositorio.findByCorreoAndUnEstado(correo,this.estadosService.traerEstado(1L));
        if(optional.isPresent()){
            return optional.get();

        }
        return null;
    }

    public UsuariosModel buscarPorNombre(String nombre){

        return this.repositorio.findByNombre(nombre);
    }



    public List<UsuariosResponseDTO> traerUsuariosDTO(){

        List<UsuariosModel> listaUsuarios= this.listar();
        List<UsuariosResponseDTO> listaUsuariosDto = new ArrayList<>();
        for(UsuariosModel usu : listaUsuarios){

            UsuariosResponseDTO dto = new UsuariosResponseDTO(usu.getId(),usu.getNombre(),
                    usu.getCorreo(), usu.getUnPerfil().getId(),
                    usu.getUnEstado().getId(),usu.getUnEstado().getNombre());

            listaUsuariosDto.add(dto);

        }

        return listaUsuariosDto;

    }

    public UsuariosModel buscarPorCorreo(String correo) {
        return this.repositorio.findByCorreo(correo);
    }

    public List<UsuariosModel> buscarUsuariosPorPerfil(Long id){

        return this.repositorio.findByUnPerfilId(id);

    }

}
