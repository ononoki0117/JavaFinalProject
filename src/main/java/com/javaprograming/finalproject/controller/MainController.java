package com.javaprograming.finalproject.controller;

import com.javaprograming.finalproject.models.Product;
import com.javaprograming.finalproject.payload.request.LoginRequest;
import com.javaprograming.finalproject.payload.response.ProductResponse;
import com.javaprograming.finalproject.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class MainController {
    private final HttpServletRequest httpServletRequest;

    public MainController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        HttpSession session = httpServletRequest.getSession(false);

        List<Product> products = new ArrayList<>();

        products =  productRepository.findAll();

        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products) {
            productResponses.add(new ProductResponse(product));
        }

        model.addAttribute("products", productResponses);

        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        if (session == null) {
            logger.info("session is null");
            model.addAttribute("login", false);
            return "main";
        }
        if (session.getAttribute("userinfo") != null) {
            logger.info("user is " + session.getAttribute("userinfo"));
            model.addAttribute("login", true);
            return "main";
        } else {
            logger.info("userinfo is null");
            model.addAttribute("login", false);
            return "main";
        }
    }

    @GetMapping("/message")
    public String message(Model model, @ModelAttribute("msg") String msg, @ModelAttribute("url") String url) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        logger.info(msg + " " + url);

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        return "message";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("cart") == null) {
            model.addAttribute("msg", "카트에 담긴 물건이 없습니다!");
            model.addAttribute("url", "../");
            return "message";
        }


        return "cart";
    }
}
