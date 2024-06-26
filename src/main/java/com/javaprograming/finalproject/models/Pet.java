package com.javaprograming.finalproject.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("pet")
public class Pet {
    @Id
    private String id;

    private String name;

    private int age;

    private String gender;

    private String breed;

    private String owner;

    public Pet() {
    }

    public Pet(String name, int age, String gender, String breed, String owner) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.breed = breed;
        this.owner = owner;
    }
}
