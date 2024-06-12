package com.javaprograming.finalproject.repository;


import com.javaprograming.finalproject.models.Pet;
import com.javaprograming.finalproject.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PetRepository extends MongoRepository<Pet, String> {
    Optional<Pet> findByOwner(String owner);
}
