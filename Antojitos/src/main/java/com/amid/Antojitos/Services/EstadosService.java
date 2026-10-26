package com.amid.Antojitos.Services;

import com.amid.Antojitos.Modelos.EstadosModel;
import com.amid.Antojitos.Repositorios.IEstadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadosService {
    @Autowired
    private IEstadosRepository repositorio;

    public List<EstadosModel> traerEstados(){

        return this.repositorio.findAll(Sort.by("id").ascending());
    }

    public EstadosModel traerEstado(Long id){

        Optional<EstadosModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){

            return optional.get();
        }
        return null;
    }

    public void guardar (EstadosModel modelo){

        this.repositorio.save(modelo);

    }

    public void eliminar (Long id){

        this.repositorio.deleteById(id);
    }

    public List<EstadosModel> traerEstadosById(List<Long> ids){

        return this.repositorio.findByIdIn(ids);
    }

}
