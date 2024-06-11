package com.javaprograming.finalproject.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Document(collection = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String password;
    @Column(unique = true)
    private String username;

    private String phone;
    private String address;

    public UserModel() {

    }
}
