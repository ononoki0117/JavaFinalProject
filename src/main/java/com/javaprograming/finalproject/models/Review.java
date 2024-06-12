package com.javaprograming.finalproject.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "review")
public class Review {
    @Id
    private String id;

    @DBRef
    private Pet pet;

    @DBRef
    private Product product;

    private String content;
}
