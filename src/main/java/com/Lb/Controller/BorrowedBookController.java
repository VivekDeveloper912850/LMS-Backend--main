package com.Lb.Controller;

/*
import com.Lb.Entity.BorrowedBook;
import com.Lb.service.BorrowedBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowed-books")
@CrossOrigin(origins = "http://localhost:5173")
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long studentId, @RequestParam Long bookId) {
        return ResponseEntity.ok(borrowedBookService.borrowBook(studentId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long borrowedBookId) {
        return ResponseEntity.ok(borrowedBookService.returnBook(borrowedBookId));
    }

    @GetMapping
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBookss() {
        return ResponseEntity.ok(borrowedBookService.getBorrowedBooks());
    }

}

 */