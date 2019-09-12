package com.castro.demo;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProfesorControlador {

    private final ProfesorRepositorio repositorio;

    ProfesorControlador(ProfesorRepositorio repositorio){
        this.repositorio = repositorio;
    }

    //Agregar Raiz

    @GetMapping("/profesores")
    List<Profesor> all(){
        return repositorio.findAll();
    }

    @PostMapping("/profesores")
    Profesor nuevoProfesor(@RequestBody Profesor nuevoProfesor){
        return repositorio.save(nuevoProfesor);
    }

    //Items singulares

    @GetMapping("/profesores/{id}")
    Profesor uno(@PathVariable Long id){
        return repositorio.findById(id)
                .orElseThrow(() -> new ProfesorNoEncontradoException(id));
    }

    @PutMapping("/profesores/{id}")
    Profesor reemplazarProfesor(@RequestBody Profesor nuevoProfesor, @PathVariable Long id){
        return repositorio.findById(id)
                .map(profesor -> {
                   profesor.setNombre(nuevoProfesor.getNombre());
                   profesor.setCelNum(nuevoProfesor.getCelNum());
                   return repositorio.save(profesor);
                })
                .orElseGet(() -> {
                    nuevoProfesor.setId(id);
                    return repositorio.save(nuevoProfesor);
                });
    }

    @DeleteMapping("/profesores/{id}")
    void borrarProfesor(@PathVariable Long id){
        repositorio.deleteById(id);
    }
}
