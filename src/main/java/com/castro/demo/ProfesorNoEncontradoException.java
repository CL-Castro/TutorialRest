package com.castro.demo;

public class ProfesorNoEncontradoException extends RuntimeException {
    public ProfesorNoEncontradoException(Long id) {
        super("No se pudo encontrar al profesor "+ id);
    }
}
