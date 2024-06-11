package com.javaprograming.finalproject.controller;

import com.javaprograming.finalproject.model.Role;
import com.javaprograming.finalproject.model.User;
import com.javaprograming.finalproject.model.UserRepository;
import com.javaprograming.finalproject.model.UserRole;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ResourceController {
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/save")
    public String save(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole);

        user.setUserRoles(roles);

        userRepository.save(user);

        return "Saved";
    }
}
