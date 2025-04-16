package com.Lb.Controller;

import com.Lb.Entity.User;
import com.Lb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://library-manageme-git-5ba4d7-vivekkumar912850-gmailcoms-projects.vercel.app") // Adjust for React URL
public class UserController {
    @Autowired
    private UserService userService;



//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody User user) {
//        return ResponseEntity.ok(userService.registerUser(user));
//    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = userService.registerUser(user);

        if (result.equals("User already registered")) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
       User  user = userService.loginUser(credentials.get("email"), credentials.get("password"));
        if (user != null) {
            return ResponseEntity.ok(user.getId());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }


    @GetMapping("/user-id")
    public ResponseEntity<?> getUserId() {
        User user = userService.getUserByEmail();

        System.out.println(user.getId());

        if (user != null) {
            return ResponseEntity.ok(user.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("userId", null));
    }
//    @GetMapping("/id")
//    public ResponseEntity<?> getUserId(){
//        User user = new User();
//        return ResponseEntity.ok(user.getId());
//    }
    @GetMapping("/user/{userId}")  // New API for fetching user profile
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
            //response.put("profilePhoto", user.getProfilePhoto()); // Assuming you store profile photo URL
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }


    // image problems solve

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("profileImage") MultipartFile file) {
        String fileName = userService.saveProfileImage(file);
        return ResponseEntity.ok(Collections.singletonMap("filename", fileName));
    }

    @GetMapping("/user-profile")
    public ResponseEntity<User> getUserProfile() {
        User user = userService.getUser();  // Get user from DB
        return ResponseEntity.ok(user);
    }
}
