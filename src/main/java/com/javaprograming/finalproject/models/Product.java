package com.javaprograming.finalproject.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "product")
public class Product {
    @Id
    private String id;

    private String name;

    private String description;

    private int price;

    private String image;

    private Boolean resolved = false;
}
