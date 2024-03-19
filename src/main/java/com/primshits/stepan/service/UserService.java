package com.primshits.stepan.service;

import com.primshits.stepan.model.MyUser;
import com.primshits.stepan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    public void addUser(MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        repository.save(user);
    }

    public Optional<MyUser> findByUsername(String username){
        return repository.findByName(username);
    }
}
