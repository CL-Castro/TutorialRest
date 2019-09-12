package com.castro.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioOrden extends JpaRepository<Orden, Long> {
}
