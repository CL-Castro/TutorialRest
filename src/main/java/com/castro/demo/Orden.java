package com.castro.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.hateoas.*;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Orden {

    private @Id @GeneratedValue Long id;

    private String descripcion;
    private Status status;

    public Orden() {
    }

    public Orden(String descripcion, Status status) {
        this.descripcion = descripcion;
        this.status = status;
    }
}
