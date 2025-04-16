package com.Lb.service;


import com.Lb.Entity.User;
import com.Lb.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private String email = "";
    @Autowired
    private UserRepository userRepository;

//    public User registerUser(User user) {
//        return userRepository.save(user);
//    }


    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());

        if (existingUser.isPresent()) {
            return "User already registered";
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    public User loginUser(String email, String password) {
            this.email = email;
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public User getUserByEmail() {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    //  image save

    public String saveProfileImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            user.setProfileImage(fileName);
            userRepository.save(user);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed");
        }
    }

    public User getUser() {
        return userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }

}