package com.javaprograming.finalproject.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private String content;
    private int age;
    private String gender;
    private String breed;
}

