package com.coderace.delivery;

import com.coderace.model.enums.DeliveryType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DeliveryType type;

    @Column(name = "date")
    private LocalDateTime date;


    public Delivery(String code, DeliveryType type) {
        this.code = code;
        this.type = type;
        this.date = null;
    }


    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public DeliveryType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

