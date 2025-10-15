package com.MallareddyCollegeOfTechnology.app.Services;


import com.MallareddyCollegeOfTechnology.app.Entities.User;
import com.MallareddyCollegeOfTechnology.app.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findByName(String name) {
        return userRepository.findByName(name);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
