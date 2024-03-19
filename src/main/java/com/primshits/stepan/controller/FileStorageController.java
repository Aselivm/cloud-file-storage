package com.primshits.stepan.controller;

import com.primshits.stepan.model.MyUser;
import com.primshits.stepan.service.AuthService;
import com.primshits.stepan.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Log
public class FileStorageController {

    @Autowired
    private AuthService authService;
    private final UserService service;

    public FileStorageController(UserService service) {
        this.service = service;
    }


    @GetMapping("/home")
    public String welcome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "home";
    }

    @PostMapping("/authorize")
    public String loginProcessing(@ModelAttribute MyUser user, Model model){
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new MyUser());
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("user",new MyUser());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String addUser(@ModelAttribute MyUser user) {
        log.info("sign-up post mapping");
        service.addUser(user);
        return "redirect:/welcome";
    }
}
