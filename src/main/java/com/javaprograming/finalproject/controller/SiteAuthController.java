package com.javaprograming.finalproject.controller;

import com.javaprograming.finalproject.models.ERole;
import com.javaprograming.finalproject.models.Pet;
import com.javaprograming.finalproject.models.Role;
import com.javaprograming.finalproject.models.User;
import com.javaprograming.finalproject.payload.request.LoginRequest;
import com.javaprograming.finalproject.payload.request.SignupRequest;
import com.javaprograming.finalproject.repository.PetRepository;
import com.javaprograming.finalproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/auth")
public class SiteAuthController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(SiteAuthController.class);
    private final HttpServletRequest httpServletRequest;

    public SiteAuthController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/signin")
    public String getSignin() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signinSubmit(Model model, @ModelAttribute("username") String username, @ModelAttribute("password") String password) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

        String url = "http://localhost:8080/api/auth/signin";

        RestTemplate restTemplate = new RestTemplate();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);

        logger.info(restTemplate.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        logger.info(response.getBody());


        if (response.getStatusCode().is2xxSuccessful()) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("userinfo", response.getBody());
            return "redirect:../";
        } else {
            model.addAttribute("msg", "Invalid username or password");
            model.addAttribute("url", "/login");
            return "redirect:../message";
        }
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String attemptSignup(Model model, @ModelAttribute("username") String username,
                                @ModelAttribute("password") String password,
                                @ModelAttribute("name") String name,
                                @ModelAttribute("phone") String phone,
                                @ModelAttribute("address") String address,
                                @ModelAttribute("pet-name") String petname,
                                @ModelAttribute("age") int age,
                                @ModelAttribute("gender") String gender,
                                @ModelAttribute("breed") String breed) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

        logger.info(username + " " + password + " " + name + " " + phone + " " + address);

        String url = "http://localhost:8080/api/auth/signup";

        RestTemplate restTemplate = new RestTemplate();

        Set<String> roles = new HashSet<>();
        roles.add("user");

        SignupRequest signupRequest = SignupRequest.builder()
                .username(username)
                .password(password)
                .address(address)
                .phone(phone)
                .name(name)
                .roles(roles)
                .build();

        HttpEntity<SignupRequest> request = new HttpEntity<>(signupRequest);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        logger.info(response.getBody());

        if (response.getStatusCode().is2xxSuccessful()) {
            Optional<User> user = userRepository.findByUsername(username);

            Pet pet = new Pet(petname, age, gender, breed, user.get().getId());

            petRepository.save(pet);

            model.addAttribute("msg", "회원 가입을 축하드립니다 ");
            model.addAttribute("url", "../auth/signin");
            return "message";
        } else {
            model.addAttribute("msg", "........");
            model.addAttribute("url", "../auth/signup");
            return "message";
        }
    }

    @GetMapping("/signout")
    public String signout(Model model) {
        HttpSession session = httpServletRequest.getSession(false);
        session.removeAttribute("userinfo");
        return "redirect:../";
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public String httpClientErrorException(Model model, Exception e) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        logger.info(e.getMessage());
        model.addAttribute("msg", "존재하지 않는 ID 이거나 정확하지 않는 비밀번호입니다");
        model.addAttribute("url", "../auth/signin");
        return "message";
    }

    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(Model model, Exception e) {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        logger.info(e.getMessage());
        model.addAttribute("msg", "패스워드를 6자 이상 설정해 주세요");
        model.addAttribute("url", "../auth/signup");
        return "message";
    }
}
