package com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services;

import com.MallareddyCollegeOfTechnology.app.Entities.User;
import com.MallareddyCollegeOfTechnology.app.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    //Spring Security calls this method when a user tries to log in
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        // Fetch user
        User user = userRepository.findByName(name);
        System.out.println("✅ User found: " + user.getName());

        // Convert enum role to Spring Security format: "ROLE_STUDENT" or "ROLE_TEACHER"
        String role = "ROLE_" + user.getRole().name();

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }

}
