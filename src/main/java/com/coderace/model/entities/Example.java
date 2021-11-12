package com.coderace.model.entities;

import com.coderace.model.enums.ExampleEnum;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "examples")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "long_value")
    private Long longValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "date_value")
    private LocalDateTime dateValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_value")
    private ExampleEnum enumValue;


    public Example(Long longValue, Double doubleValue, String stringValue, LocalDateTime dateValue, ExampleEnum enumValue) {
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
        this.dateValue = dateValue;
        this.enumValue = enumValue;
    }
}
