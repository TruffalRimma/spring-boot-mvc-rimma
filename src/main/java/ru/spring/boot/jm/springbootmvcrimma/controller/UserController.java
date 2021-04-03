package ru.spring.boot.jm.springbootmvcrimma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.boot.jm.springbootmvcrimma.model.User;
import ru.spring.boot.jm.springbootmvcrimma.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPage(Model model, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        User user1 = userService.loadUserByUsername(user.getUsername());
        model.addAttribute("user", user1);
        return "user";
    }
}
