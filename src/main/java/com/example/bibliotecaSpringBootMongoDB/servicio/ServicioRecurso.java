package com.example.bibliotecaSpringBootMongoDB.servicio;

import com.example.bibliotecaSpringBootMongoDB.dto.RecursoDTO;
import com.example.bibliotecaSpringBootMongoDB.mapper.RecursoMapper;
import com.example.bibliotecaSpringBootMongoDB.modelo.Recurso;
import com.example.bibliotecaSpringBootMongoDB.repositorio.RepositorioRecurso;
import com.example.bibliotecaSpringBootMongoDB.utils.Mensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ServicioRecurso {

    @Autowired
    RepositorioRecurso repository;
    RecursoMapper mapper = new RecursoMapper();

    public RecursoDTO crear(RecursoDTO recursoDTO) {
        Recurso recurso = mapper.fromDTO(recursoDTO);
        return mapper.fromCollection(repository.save(recurso));
    }

    public RecursoDTO encontrarPorID(String id){
        Recurso recurso = repository.findById(id).orElseThrow(()-> new RuntimeException("No se encuentra el recurso"));
        return mapper.fromCollection(recurso);
    }

    public List<RecursoDTO> buscarTodos() {
        List<Recurso> recurso = repository.findAll();
        return mapper.fromCollectionList(recurso);
    }

    public RecursoDTO guardar(RecursoDTO recursoDTO) {
        Recurso recurso = mapper.fromDTO(recursoDTO);
        return mapper.fromCollection(repository.save(recurso));
    }

    public void borrarRecursoPorId(String id) {
        repository.deleteById(id);
    }

    public Mensaje comprobarDisponibilidad(String id){
        RecursoDTO recursoDTO = encontrarPorID(id);
        return mensajeDisponibilidad(recursoDTO);
    }

    private Mensaje mensajeDisponibilidad(RecursoDTO recursoDTO) {
        if(recursoDTO.isDisponible()){
            return new Mensaje(true,  "El material está disponible");
        }
        return new Mensaje(false,"El material no está disponible, fue prestado el: "+ recursoDTO.getFecha());
    }

    private Mensaje mensajeDevolver(RecursoDTO recursoDTO) {
        if(!recursoDTO.isDisponible()){
            return new Mensaje(true,  "El material fue devuelto con éxito");
        }
        return new Mensaje(false,"El material no está prestado");
    }

    public Mensaje prestarRecurso(String id){
        RecursoDTO recursoDTO = encontrarPorID(id);
        Mensaje mensaje = mensajeDisponibilidad(recursoDTO);
        if(recursoDTO.isDisponible()){
            recursoDTO.setDisponible(false);

            Recurso recurso = mapper.fromDTO(recursoDTO);
            mapper.fromCollection(repository.save(recurso));
        }
        return mensaje;
    }

    public  Mensaje devolverRecurso(String id){
        RecursoDTO recursoDTO= encontrarPorID(id);
        Mensaje mensaje = mensajeDevolver(recursoDTO);
        if(!recursoDTO.isDisponible()){
            recursoDTO.setDisponible(true);
            recursoDTO.setFecha(null);
            Recurso recurso = mapper.fromDTO(recursoDTO);
            mapper.fromCollection(repository.save(recurso));
        }
        return mensaje;
    }

    public List<RecursoDTO> buscarPorArea(String area){
        List<Recurso> recursos = repository.findByArea(area);
        return mapper.fromCollectionList(recursos);
    }

    public List<RecursoDTO> buscarPorTipo(String tipo){
        List<Recurso> recursos = repository.findByTipo(tipo);
        return mapper.fromCollectionList(recursos);
    }

    public List<RecursoDTO> buscarPorAreaYTipo(String area, String tipo){
        List<Recurso> recursos = repository.findByAreaAndTipo(area, tipo);
        return mapper.fromCollectionList(recursos);
    }
}
