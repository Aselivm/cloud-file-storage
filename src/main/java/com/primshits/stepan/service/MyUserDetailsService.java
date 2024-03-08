package com.primshits.stepan.service;

import com.primshits.stepan.model.MyUser;
import com.primshits.stepan.repository.UserRepository;
import com.primshits.stepan.config.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public MyUserDetailsService() {

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByName(username);
        return user.map(MyUserDetails::new).orElseThrow(()->new UsernameNotFoundException(username+" not found"));
    }
}
