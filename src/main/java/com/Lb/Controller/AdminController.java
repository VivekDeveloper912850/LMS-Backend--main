package com.Lb.Controller;


import com.Lb.Entity.Admin;
import com.Lb.Entity.Book;
import com.Lb.Entity.User;
import com.Lb.Repo.BookRepository;
import com.Lb.Repo.UserRepository;
import com.Lb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("https://library-manageme-git-5ba4d7-vivekkumar912850-gmailcoms-projects.vercel.app")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> credentials) {
        Admin admin = adminService.loginAdmin(credentials.get("email"), credentials.get("password"));
        if (admin != null) {
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

     @GetMapping("/books")
      public List<Book> getAllBooks() {
       return bookRepository.findAll();
   }
}
