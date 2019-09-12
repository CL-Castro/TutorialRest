package com.castro.demo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBAProfesor {

    @Bean
    CommandLineRunner initDB(ProfesorRepositorio repositorio){
        return args -> {
          log.info("Precarga "+ repositorio.save(new Profesor("Bilbo Baggins", 72516499L)));
          log.info("Precarga "+ repositorio.save(new Profesor("Frodo Baggins", 77743818L)));
        };
    }
}
