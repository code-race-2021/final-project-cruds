package com.coderace.model.entities;

import com.coderace.model.enums.ServiceType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "sku")
    private String sku;


    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")

    private ServiceType serviceType;

    @Column(name = "days")
    private Long days;


    public Service() {
    }

    public Service(String sku, ServiceType serviceType, Long days) {
        this.sku = sku;
        this.serviceType = serviceType;
        this.days = days;
    }
}


