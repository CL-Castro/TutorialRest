package com.castro.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Component
class CreadorRecursoProfesor implements ResourceAssembler<Profesor, Resource<Profesor>> {

    @Override
    public Resource<Profesor> toResource(Profesor profesor) {
        return new Resource<>(profesor,
                linkTo(methodOn(ProfesorControlador.class).uno(profesor.getId())).withSelfRel(),
                linkTo(methodOn(ProfesorControlador.class).all()).withRel("profesores"));
    }
}
