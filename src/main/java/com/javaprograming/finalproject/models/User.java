package com.javaprograming.finalproject.models;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String address;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String phone;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String address, String password, String phone, String name) {
        this.username = username;
        this.address = address;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }
}
