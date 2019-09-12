package com.castro.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Profesor {
    private @Id @GeneratedValue Long id;
    private String nombre;
    private Long celNum;

    Profesor(){}

    Profesor(String nombre, Long celNum){
        this.nombre = nombre;
        this.celNum = celNum;
    }
}
