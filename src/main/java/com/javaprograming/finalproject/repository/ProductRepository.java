package com.javaprograming.finalproject.repository;

import com.javaprograming.finalproject.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAll();
    Product findOneById(String id);
}

