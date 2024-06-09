package com.javaprograming.finalproject.controller;

import com.javaprograming.finalproject.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/login")
    public String getLogin(Model model) {
        HttpSession session = httpServletRequest.getSession();

        if (session == null) {
            model.addAttribute("login", false);
        } else if (session.getAttribute("username") != null) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }
        return "login";
    }

    @PostMapping("/login")
    public String postLoginProcess(Model model, @RequestParam String username, @RequestParam String password) {
        if (authService.findUser(username)) {
            if (authService.authUser(username, password)) {
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("username", username);

                return "redirect:/";
            } else {
                model.addAttribute("msg", "ID가 존재하지 않거나 비밀번호가 틀렸습니다!");
                model.addAttribute("url", "/login");
                return "message";
            }
        } else {
            model.addAttribute("msg", "ID가 존재하지 않거나 비밀번호가 틀렸습니다!");
            model.addAttribute("url", "/login");
            return "message";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        HttpSession session = httpServletRequest.getSession(true);
        session.invalidate();
        return "index";
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {

        HttpSession session = httpServletRequest.getSession();

        if (session == null) {
            model.addAttribute("login", false);
        } else if (session.getAttribute("username") != null) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(Model model,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String phone,
                             @RequestParam String address) {
        if (authService.findUser(username)) {
            model.addAttribute("msg", "이미 가입된 아이디 입니다!");
            model.addAttribute("url", "/signup");
            return "message";
        } else {
            authService.saveUser(username, password, name, phone, address);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("username", username);
            model.addAttribute("msg", "회원 가입을 축하드립니다!");
            model.addAttribute("url", "/");
            return "message";
        }
    }
}
