package com.coderace.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "customers")
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dni")
    private long dni;

    @Column(name = "email")
    private String email;

    public Customer(String name, long dni, String email) {
        this.name = name;
        this.dni = dni;
        this.email = email;
    }
}

