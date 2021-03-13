package com.hillel.artemjev.homework24.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Director {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDay;
}
