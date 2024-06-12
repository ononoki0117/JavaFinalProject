package com.javaprograming.finalproject.repository;


import com.javaprograming.finalproject.models.Product;
import com.javaprograming.finalproject.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<List<Review>> findByProduct(Product product);
}
