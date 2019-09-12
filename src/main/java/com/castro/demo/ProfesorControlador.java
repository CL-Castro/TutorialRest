package com.castro.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Resource;

import javax.xml.bind.annotation.XmlRootElement;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@XmlRootElement
@RestController
public class ProfesorControlador {

    private final ProfesorRepositorio repositorio;

    private final CreadorRecursoProfesor creador;

    ProfesorControlador(ProfesorRepositorio repositorio,
                        CreadorRecursoProfesor creador){
        this.repositorio = repositorio;
        this.creador = creador;
    }

    //Agregar Raiz

    @GetMapping("/profesores")
    Resources<Resource<Profesor>> all(){
        List<Resource<Profesor>> profesores = repositorio.findAll().stream()
                .map(creador::toResource)
                .collect(Collectors.toList());
        return new Resources<>(profesores,
                linkTo(methodOn(ProfesorControlador.class).all()).withSelfRel());
    }

    @PostMapping("/profesores")
    ResponseEntity<?> nuevoProfesor(@RequestBody Profesor nuevoProfesor) throws URISyntaxException {
        Resource<Profesor> recurso = creador.toResource(repositorio.save(nuevoProfesor));

        return ResponseEntity
                .created(new URI(recurso.getId().expand().getHref()))
                .body(recurso);
    }

    //Items singulares

    @GetMapping("/profesores/{id}")
     Resource<Profesor> uno(@PathVariable Long id){

        Profesor profesor = repositorio.findById(id)
                .orElseThrow(() -> new ProfesorNoEncontradoException(id));

        return creador.toResource(profesor);
    }

    @PutMapping("/profesores/{id}")
    ResponseEntity<?> reemplazarProfesor(@RequestBody Profesor nuevoProfesor, @PathVariable Long id) throws URISyntaxException{
        Profesor profesorModificado = repositorio.findById(id)
                .map(profesor -> {
                    profesor.setNombre(nuevoProfesor.getNombre());
                    profesor.setCelNum(nuevoProfesor.getCelNum());
                    return repositorio.save(profesor);
                })
                .orElseGet(() -> {
                    nuevoProfesor.setId(id);
                    return repositorio.save(nuevoProfesor);
                });

        Resource<Profesor> recurso = creador.toResource(profesorModificado);
        return ResponseEntity
                .created(new URI(recurso.getId().expand().getHref()))
                .body(recurso);

    }

    @DeleteMapping("/profesores/{id}")
    ResponseEntity<?> borrarEmpleado(@PathVariable Long id){
        repositorio.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
