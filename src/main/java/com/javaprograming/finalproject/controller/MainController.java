package com.javaprograming.finalproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final HttpServletRequest httpServletRequest;

    public MainController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/")
    public String index(Model model) {
        HttpSession session = httpServletRequest.getSession(false);

        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        if (session == null) {
            logger.info("session is null");
            model.addAttribute("login", false);
            return "index";
        }
        if (session.getAttribute("username") != null) {
            logger.info("username is " + session.getAttribute("username"));
            model.addAttribute("login", true);
            return "index";
        } else {
            logger.info("username is null");
            model.addAttribute("login", false);
            return "index";
        }
    }

}
