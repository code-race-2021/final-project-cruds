package com.coderace.model.entities;

import com.coderace.model.enums.DeliveryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "deliveries")
@Getter
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
}

