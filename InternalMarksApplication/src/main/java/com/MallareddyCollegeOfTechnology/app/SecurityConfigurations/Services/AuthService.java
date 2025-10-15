package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services;

import com.MallareddyCollegeOfTechnology.app.Entities.User;
import com.MallareddyCollegeOfTechnology.app.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByName(username);
        }

        throw new UsernameNotFoundException("User not authenticated");
    }
}
