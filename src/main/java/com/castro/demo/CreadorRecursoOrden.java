package com.castro.demo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class CreadorRecursoOrden implements ResourceAssembler<Orden, Resource<Orden>>{

    @Override
    public Resource<Orden> toResource(Orden orden) {
        Resource<Orden> recursoOrden = new Resource<>(orden,
                linkTo(methodOn(OrdenControlador.class).uno(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdenControlador.class).all()).withRel("ordenes"));

        if (orden.getStatus() == Status.EN_PROGRESO) {
            recursoOrden.add(
                    linkTo(methodOn(OrdenControlador.class)
                            .cancel(orden.getId())).withRel("cancel"));
            recursoOrden.add(
                    linkTo(methodOn(OrdenControlador.class)
                            .complete(orden.getId())).withRel("completo"));
        }

        return recursoOrden;
    }
}
