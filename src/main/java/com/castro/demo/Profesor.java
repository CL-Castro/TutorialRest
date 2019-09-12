package com.castro.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Resource;
@XmlRootElement
@Data
@Entity
class Profesor {
    private @Id @GeneratedValue Long id;
    private String primerNombre;
    private String apellidoPaterno;
    private Long celNum;

    Profesor(){}

    public Profesor(String primerNombre, String apellidoPaterno, Long celNum) {
        this.primerNombre = primerNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.celNum = celNum;
    }

    public String getNombre(){
        return this.primerNombre + " " + this.apellidoPaterno;
    }

    public  void setNombre(String nombre){
        String[] partes = nombre.split(" ");
        this.primerNombre = partes[0];
        this.apellidoPaterno = partes[1];
    }
}
