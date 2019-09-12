package com.castro.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProfesorNoEncontradoConsejo {

    @ResponseBody
    @ExceptionHandler(ProfesorNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String profesorNoEncontradoHandler(ProfesorNoEncontradoException ex){
        return ex.getMessage();
    }
}
