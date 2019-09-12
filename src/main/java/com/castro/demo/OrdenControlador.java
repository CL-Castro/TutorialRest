package com.castro.demo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class OrdenControlador {
    private final RepositorioOrden repositorioOrden;
    private final CreadorRecursoOrden creador;

    public OrdenControlador(RepositorioOrden repositorioOrden, CreadorRecursoOrden creador) {
        this.repositorioOrden = repositorioOrden;
        this.creador = creador;
    }

    @GetMapping("/ordenes")
    Resources<Resource<Orden>> all() {

        List<Resource<Orden>> ordenes = repositorioOrden.findAll().stream()
                .map(creador::toResource)
                .collect(Collectors.toList());

        return new Resources<>(ordenes,
                linkTo(methodOn(OrdenControlador.class).all()).withSelfRel());
    }

    @GetMapping("/ordenes/{id}")
    Resource<Orden> uno(@PathVariable Long id){
        return creador.toResource(
                repositorioOrden.findById(id)
                .orElseThrow(() -> new OrderNoEncontradaExcepcion(id))
        );
    }

    @PostMapping("/ordenes")
    ResponseEntity<Resource<Orden>> nuevaOrden(@RequestBody Orden orden){

        orden.setStatus(Status.EN_PROGRESO);
        Orden nuevaOrden = repositorioOrden.save(orden);

        return ResponseEntity
                .created(linkTo(methodOn(OrdenControlador.class).uno(nuevaOrden.getId())).toUri())
                .body(creador.toResource(nuevaOrden));
    }

    @DeleteMapping("/ordenes/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

        Orden orden = repositorioOrden.findById(id).orElseThrow(() -> new OrderNoEncontradaExcepcion(id));

        if (orden.getStatus() == Status.EN_PROGRESO) {
            orden.setStatus(Status.CANCELADO);
            return ResponseEntity.ok(creador.toResource(repositorioOrden.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Metodo no permitido", "No es posible cancelar una orden en " + orden.getStatus() + " status"));
    }

    @PutMapping("/ordenes/{id}/complete")
    ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {

        Orden orden = repositorioOrden.findById(id).orElseThrow(() -> new OrderNoEncontradaExcepcion(id));

        if (orden.getStatus() == Status.EN_PROGRESO) {
            orden.setStatus(Status.COMPLETADO);
            return ResponseEntity.ok(creador.toResource(repositorioOrden.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Metodo no permitido", "No es posible completar una orden en " + orden.getStatus() + " status"));
    }
}
