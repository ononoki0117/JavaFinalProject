package com.javaprograming.finalproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaprograming.finalproject.models.Pet;
import com.javaprograming.finalproject.models.Product;
import com.javaprograming.finalproject.models.User;
import com.javaprograming.finalproject.payload.request.LoginRequest;
import com.javaprograming.finalproject.payload.response.ProductResponse;
import com.javaprograming.finalproject.payload.response.UserInfoResponse;
import com.javaprograming.finalproject.repository.PetRepository;
import com.javaprograming.finalproject.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    private final HttpServletRequest httpServletRequest;

    public MainController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PetRepository petRepository;

    @GetMapping("/")
    public String index(Model model) {
        HttpSession session = httpServletRequest.getSession(true);

        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products) {
            productResponses.add(new ProductResponse(product));
        }

        model.addAttribute("products", productResponses);

        if (session.getAttribute("userinfo") != null) {
            model.addAttribute("login", true);

            String info = (String) session.getAttribute("userinfo");

            if (session.getAttribute("pet") != null) {
                Pet pet = (Pet) session.getAttribute("pet");
                model.addAttribute("pet", pet);
                return "main";
            }

            Logger logger = LoggerFactory.getLogger(MainController.class);
            logger.info(info);

            ObjectMapper mapper = new ObjectMapper();
            UserInfoResponse userInfoResponse = null;
            try {
                userInfoResponse = mapper.readValue(info, UserInfoResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
                return "main";
            }
            String user = userInfoResponse.getId();

            Pet pet = new Pet();
            if (petRepository.findByOwner(user).isPresent()) {
                logger.info(pet.getId());
                pet = petRepository.findByOwner(user).get();
            } else logger.info("pet not found");

            session.setAttribute("pet", pet);
            model.addAttribute("pet", pet);


        } else {
            model.addAttribute("login", false);
        }
        return "main";

    }

    @GetMapping("/message")
    public String message(Model model, @ModelAttribute("msg") String msg, @ModelAttribute("url") String url) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        logger.info(msg + " " + url);

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        return "message";
    }

    @GetMapping("/cart/add")
    public String addCart(Model model, @RequestParam String id) {
        HttpSession session = httpServletRequest.getSession(true);

        Product product = productRepository.findOneById(id);
        if (product == null) {
            model.addAttribute("msg", "존재하지 않는 상품입니다!");
            model.addAttribute("url", "/");
            return "message";
        }

        CartItem item = CartItem.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .build();

        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<CartItem>());
        }

        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cart");
        cartItems.add(item);
        session.setAttribute("cart", cartItems);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<CartItem>());
        }

        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cart");
        int sum = 0;
        for (CartItem cartItem : cartItems) {
            sum += cartItem.getPrice();
        }
        session.setAttribute("sum", sum);
        model.addAttribute("sum", sum);
        model.addAttribute("items", cartItems);

        if (session.getAttribute("userinfo") != null) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "cart";
    }

    @GetMapping("/pay")
    public String pay(Model model, @RequestParam String id) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("userinfo") == null) {
            model.addAttribute("msg", "결제를 위해 로그인이 필요합니다");
            model.addAttribute("url", "/auth/signin");
            return "message";
        }

        Product product = productRepository.findOneById(id);
        if (product == null) {
            model.addAttribute("msg", "존재하지 않는 상품입니다!");
            model.addAttribute("url", "/");
            return "message";
        }

        CartItem item = CartItem.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .build();

        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(item);

        model.addAttribute("items", cartItems);

        return "pay";
    }

    @PostMapping("/pay")
    public String pay(Model model) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("cart") == null) {
            model.addAttribute("msg", "장바구니에 상품이 존재하지 않습니다!");
            model.addAttribute("url", "/");
            return "message";
        }

        if (session.getAttribute("userinfo") == null) {
            model.addAttribute("msg", "결제를 위해 로그인이 필요합니다");
            model.addAttribute("url", "/auth/signin");
            return "message";
        }

        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cart");
        model.addAttribute("items", cartItems);

        return "pay";
    }

    @PostMapping("/pay/complete")
    public String payComplete(Model model) {
        HttpSession session = httpServletRequest.getSession(true);
        if (session.getAttribute("cart") != null) {
            session.removeAttribute("cart");
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("userinfo") == null) {
            model.addAttribute("msg", "로그인 먼저 해 주세요!");
            model.addAttribute("url", "/");
            return "message";
        }

        Pet pet = (Pet) session.getAttribute("pet");
        model.addAttribute("pet", pet);

        return "profile";
    }

    @PostMapping("/profile")
    public String profileSubmit(Model model, @ModelAttribute("name") String name, @ModelAttribute("age") int age, @ModelAttribute("gender") String gender) {
        HttpSession session = httpServletRequest.getSession(true);

        if (session.getAttribute("pet") == null) {
            return ("main");
        }

        Pet pet = (Pet) session.getAttribute("pet");
        String breed = pet.getBreed();
        String id = pet.getId();
        Pet newPet = new Pet();

        newPet.setName(name);
        newPet.setAge(age);
        newPet.setGender(gender);
        newPet.setBreed(breed);
        newPet.setId(id);
        newPet.setOwner(pet.getOwner());

        session.removeAttribute("pet");
        session.setAttribute("pet", newPet);

        model.addAttribute("pet", newPet);

        petRepository.save(newPet);
        return "redirect:/";
    }
}

@Builder
@Getter
class CartItem {
    private String id;
    private String name;
    private int price;
}