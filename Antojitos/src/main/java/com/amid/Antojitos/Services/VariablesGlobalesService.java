package com.amid.Antojitos.Services;


import com.amid.Antojitos.Modelos.VariablesGlobalesModel;
import com.amid.Antojitos.Repositorios.IVariablesGlobalesRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VariablesGlobalesService {

    @Autowired
    private IVariablesGlobalesRepostory repositorio;

    public List<VariablesGlobalesModel> listar(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public VariablesGlobalesModel traer(Long id){

        Optional<VariablesGlobalesModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (VariablesGlobalesModel modelo){

        this.repositorio.save(modelo);

    }

    public void eliminar (Long id){

        this.repositorio.deleteById(id);
    }

}
