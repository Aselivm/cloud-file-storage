package com.primshits.stepan.controller;

import com.primshits.stepan.model.MyUser;
import com.primshits.stepan.service.UserService;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Log
public class FileStorageController {
    private final UserService service;

    public FileStorageController(UserService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the unprotected page";
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
        service.addUser(user);
        return "redirect:/welcome";
    }
}
