package com.castro.demo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBAOrden {
    @Bean
    CommandLineRunner initDBO(RepositorioOrden orderRepository){
        return args -> {
            orderRepository.save(new Orden("MacBook Pro", Status.COMPLETADO));
            orderRepository.save(new Orden("iPhone", Status.EN_PROGRESO));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
