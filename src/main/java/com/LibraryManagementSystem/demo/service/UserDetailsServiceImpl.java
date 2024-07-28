package com.LibraryManagementSystem.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here, you can load user from the database
        // For this demo, we are using a hardcoded user
        if ("admin".equals(username)) {
            return new User("admin", "$2a$10$wzviU82JOFHZdLbvRJh4MeEs5VZKNzDk9G0RCGzA5nTRBRGwBypFG", // password encoded for "password"
                    Arrays.asList(() -> "ROLE_ADMIN"));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}