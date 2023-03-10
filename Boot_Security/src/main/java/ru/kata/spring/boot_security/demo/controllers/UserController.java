package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetails;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

import java.security.Principal;

@Controller
public class UserController {
    private UserDetailService userService;

    @Autowired
    public void setUserService(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }


    @GetMapping("/user_info")
    public String pageUserDetails(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
   // User user = userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user_info";
    }
}
