package com.javaprograming.finalproject.controller;

import com.javaprograming.finalproject.models.Pet;
import com.javaprograming.finalproject.models.Product;
import com.javaprograming.finalproject.models.Review;
import com.javaprograming.finalproject.payload.response.ReviewResponse;
import com.javaprograming.finalproject.repository.PetRepository;
import com.javaprograming.finalproject.repository.ProductRepository;
import com.javaprograming.finalproject.repository.ReviewRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PetRepository petRepository;

    private final HttpServletRequest httpServletRequest;

    public ProductController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("")
    public String product(Model model, @RequestParam String id) {
        Product product = productRepository.findOneById(id);
        HttpSession session = httpServletRequest.getSession(true);
        Optional<List<Review>> reviews = reviewRepository.findByProduct(product);

        if (reviews.isPresent()) {
            List<ReviewResponse> reviewResponses = new ArrayList<>();

            for (Review review : reviews.get()) {
                Pet pet = review.getPet();

                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setContent(review.getContent());
                reviewResponse.setAge(pet.getAge());
                reviewResponse.setBreed(pet.getBreed());
                reviewResponse.setGender(pet.getGender());

                reviewResponses.add(reviewResponse);
            }

            model.addAttribute("reviews", reviewResponses);

        } else {
            model.addAttribute("reviews", new ArrayList<ReviewResponse>());
        }

        if (session.getAttribute("userinfo") != null) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        model.addAttribute("product", product);
        return "product";
    }
}
