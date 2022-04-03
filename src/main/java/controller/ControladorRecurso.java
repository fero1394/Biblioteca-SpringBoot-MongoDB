package controller;

import dto.RecursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicio.ServicioRecurso;

@RestController
@RequestMapping("/recursos")
public class ControladorRecurso {

    @Autowired
    ServicioRecurso servicioRecurso;

    @PostMapping("/crear")
    public ResponseEntity<RecursoDTO> create(@RequestBody RecursoDTO recursoDTO) {
        return new ResponseEntity(servicioRecurso.crear(recursoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity<RecursoDTO> mostrarDisponibilidad(@PathVariable("id") String id) {
        return new ResponseEntity(servicioRecurso.comprobarDisponibilidad(id), HttpStatus.OK);
    }


}
