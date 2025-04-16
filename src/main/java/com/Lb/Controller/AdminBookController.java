package com.Lb.Controller;

import com.Lb.Entity.Book;
import com.Lb.Repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin("https://library-manageme-git-5ba4d7-vivekkumar912850-gmailcoms-projects.vercel.app")
public class AdminBookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok("Book added successfully!");
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setStatus("Approved");
            bookRepository.save(book);
            return ResponseEntity.ok("Book approved successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found!");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setStatus("Rejected");
            bookRepository.save(book);
            return ResponseEntity.ok("Book rejected successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }
}
