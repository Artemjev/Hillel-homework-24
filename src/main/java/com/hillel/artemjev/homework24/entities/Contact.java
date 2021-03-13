package com.hillel.artemjev.homework24.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Contact {
    private Integer id;
    private String name;
    private ContactType type;
    private String contact;

    @NoArgsConstructor
    public enum ContactType {PHONE, EMAIL,}
}
