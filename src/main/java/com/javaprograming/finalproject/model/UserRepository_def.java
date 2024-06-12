package com.javaprograming.finalproject.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository_def extends MongoRepository<UserModel, String> {
    UserModel findByLoginId(String loginId);
}
