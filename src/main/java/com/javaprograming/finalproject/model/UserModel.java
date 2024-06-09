package com.javaprograming.finalproject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "user")
public class UserModel {
    private String id;
    private String loginId;
    private String password;
    private String username;
    private String phone;
    private String address;
}
