package com.Lb.Controller;

import com.Lb.Entity.Book;
import com.Lb.Entity.BorrowedBook;
import com.Lb.Entity.ReturnedBook;
import com.Lb.Repo.BookRepository;
import com.Lb.Repo.BorrowedBookRepository;
import com.Lb.Repo.ReturnedBookRepository;
import com.Lb.Repo.UserRepository;
import com.Lb.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://library-manageme-git-5ba4d7-vivekkumar912850-gmailcoms-projects.vercel.app")
public class LibraryController {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private ReturnedBookRepository returnedBookRepository;
    @Autowired
    private  BorrowedBookService borrowedBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final int FINE_PER_DAY = 5; // Fine amount per day after due date
    private static final int MAX_BORROW_DAYS = 14; // Maximum allowed borrowing days

    @GetMapping("/borrowed-books/{userId}")
    public List<BorrowedBook> getBorrowedBooks(@PathVariable Long userId) {
        return borrowedBookRepository.findByUserId(userId);
    }


    @GetMapping("/issued-books")
    public List<BorrowedBook> getIssuedBooks() {
        return borrowedBookRepository.findAll();
    }

    @GetMapping("/returned-books/{userId}")
    public List<ReturnedBook> getReturnedBooks(@PathVariable Long userId) {
        return returnedBookRepository.findByUserId(userId);
    }



    @PostMapping("/borrow-book/{userId}/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        Optional<Book>  bookOptional = bookRepository.findById(bookId);
        // Check if user already has a borrowed book
        List<BorrowedBook> borrowedBooks = borrowedBookService.getBorrowedBooks();
        if(borrowedBooks == null){
            System.out.println("Borrowed Book is empty");
            return ResponseEntity.badRequest().body("No borrowed books found");
        }
        Book books = new Book();

        if (bookOptional.isPresent()) {
            books = bookOptional.get();
        } else {
            return ResponseEntity.badRequest().body("Book not found");
        }

        if(books.getQuantity() != 0){
            books.setQuantity(books.getQuantity() - 1);
            bookRepository.save(books);
        for (BorrowedBook book : borrowedBooks) {
            if (book.getUser().getId().equals(userId) && book.getBook().getId().equals(bookId)) {
                return ResponseEntity.badRequest().body("You have already borrowed a book. Please return it first.");
            }
        }

        // Borrow  the book
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(userRepository.findById(userId).orElseThrow());
        borrowedBook.setBook(bookRepository.findById(bookId).orElseThrow());
        borrowedBook.setBorrowedDate(LocalDateTime.now());
        borrowedBookRepository.save(borrowedBook);

        return ResponseEntity.ok("Book borrowed successfully");

        }else{
            return ResponseEntity.badRequest().body("Book is not available");
        }
    }


    @PostMapping("/return-book/{userId}/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);
        for (BorrowedBook book : borrowedBooks) {
            if (book.getBook().getId().equals(bookId)) {
                LocalDateTime borrowedDate = book.getBorrowedDate();
                LocalDateTime returnDate = LocalDateTime.now();
                long daysBorrowed = Duration.between(borrowedDate, returnDate).toDays();

                int fineAmount = 0;
                if (daysBorrowed > MAX_BORROW_DAYS) {
                    fineAmount = (int) (daysBorrowed - MAX_BORROW_DAYS) * FINE_PER_DAY;
                }

                borrowedBookRepository.delete(book);

                ReturnedBook returnedBook = new ReturnedBook();
                returnedBook.setUser(book.getUser());
                returnedBook.setBook(book.getBook());
                returnedBook.setReturnDate(returnDate);
                returnedBook.setFineAmount(fineAmount);
                returnedBookRepository.save(returnedBook);

                return fineAmount > 0 ?
                        ResponseEntity.ok("Book returned successfully. Fine Amount: Rs." + fineAmount) :
                        ResponseEntity.ok("Book returned successfully. No fine.");
            }
        }
        return ResponseEntity.badRequest().body("Book not found in borrowed list");
    }



    @PutMapping("/return-book/{borrowId}")
    public String returnBook(@PathVariable Long borrowId) {
        Optional<BorrowedBook> borrowedBookOptional = borrowedBookRepository.findById(borrowId);
        ReturnedBook returnedBook = new ReturnedBook();

        if (borrowedBookOptional.isPresent()) {
            BorrowedBook borrowedBook = borrowedBookOptional.get();
            LocalDateTime borrowedDate = borrowedBook.getBorrowedDate();
            LocalDateTime returnDate = LocalDateTime.now();
            long daysBorrowed = Duration.between(borrowedDate, returnDate).toDays();
            double fineAmount = 0;
            if (daysBorrowed > MAX_BORROW_DAYS) {
                fineAmount = (daysBorrowed - MAX_BORROW_DAYS) * FINE_PER_DAY;
            }

            if (fineAmount > 0) {
                borrowedBook.setFine(fineAmount);
                returnedBook.setFineAmount(fineAmount);
                borrowedBookRepository.save(borrowedBook);
                return "Fine of Rs." + fineAmount + " is due. Please pay before returning the book.";
            }
            // Move to ReturnedBook entity

            returnedBook.setUser(borrowedBook.getUser());
            returnedBook.setBook(borrowedBook.getBook());
            returnedBook.setReturnDate(returnDate);
            returnedBook.setFineAmount(0.0);
            returnedBook.setBorrowedDate(borrowedBook.getBorrowedDate());
            borrowedBook.setReturned(true);
            returnedBookRepository.save(returnedBook); // Save to returned books
            borrowedBookRepository.delete(borrowedBook); // Remove from borrowed books


//            borrowedBook.setFine((double) fineAmount);
//            borrowedBookRepository.save(borrowedBook);

            return "Book returned successfully. Fine: Rs." ;
        }
        return "Borrow record not found";
    }


    @GetMapping("/returned-books")
    public List<ReturnedBook> getReturnedBooks() {
        return returnedBookRepository.findAll();
    }


    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks() {
        borrowedBookRepository.deleteAll();  // Delete all borrowed books
        returnedBookRepository.deleteAll();  // Delete all returned books

        return ResponseEntity.ok("All borrowed and returned book records deleted successfully.");
    }
}

