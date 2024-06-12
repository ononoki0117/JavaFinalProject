package com.javaprograming.finalproject.repository;

import com.javaprograming.finalproject.models.ERole;
import com.javaprograming.finalproject.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
