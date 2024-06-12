package com.javaprograming.finalproject.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetResponse {
    private String name;
    private String gender;
    private int age;
}
