package com.primshits.stepan.controller;

import com.primshits.stepan.model.MyUser;
import com.primshits.stepan.repository.UserRepository;
import com.primshits.stepan.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class FileStorageController {
    private final UserService service;

    public FileStorageController(UserService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the unprotected page";
    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody MyUser user){
        service.addUser(user);
        return "User saved";
    }
}
