package com.hillel.artemjev.homework24.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    private String login;
    private String password;
    private LocalDate dateBorn;
}
