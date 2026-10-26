package com.amid.Antojitos.Services;

import com.amid.Antojitos.Modelos.PerfilModel;
import com.amid.Antojitos.Repositorios.IPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private IPerfilRepository repositorio;

    public List<PerfilModel> listar(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public PerfilModel traer(Long id){

        Optional<PerfilModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (PerfilModel modelo){

        this.repositorio.save(modelo);

    }

    public void eliminar (Long id){

        this.repositorio.deleteById(id);
    }

}
