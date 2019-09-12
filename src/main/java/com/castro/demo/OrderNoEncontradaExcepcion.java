package com.castro.demo;

public class OrderNoEncontradaExcepcion extends RuntimeException {
    public OrderNoEncontradaExcepcion(Long id) {
        super("No se pudo encontrar la orden "+ id);
    }
}
