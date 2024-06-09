package com.javaprograming.finalproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "test")
public class MongoDBTestModel {
    private String name;
    private int age;
    private String id;
}
