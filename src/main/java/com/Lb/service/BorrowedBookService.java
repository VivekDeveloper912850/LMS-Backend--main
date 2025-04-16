package com.Lb.service;


//import com.Lb.Entity.BorrowedBook;
//import com.Lb.Repo.BorrowedBookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
////import java.time.temporal.ChronoUnit;
////import javax.xml.datatype.DatatypeConstants;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class BorrowedBookService {
//
//    @Autowired
//    private BorrowedBookRepository borrowedBookRepository;
//
//    // Get borrowed books by user
//    public List<BorrowedBook> getBorrowedBooks(Long userId) {
//        return borrowedBookRepository.findByUserId(userId);
//    }
//
//    // Return a book and calculate fine
//    public String returnBook(Long borrowedBookId) {
//        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId).orElse(null);
//
//        if (borrowedBook == null) {
//            return "Book record not found!";
//        }
//
//        Date borrowedDate = borrowedBook.getBorrowedDate();
//        Date returnDate = new Date(); // Assume current date as return date
//        borrowedBook.setReturnDate(returnDate);
//
//        // Calculate difference in days
//        long diffInMillies = returnDate.getTime() - borrowedDate.getTime();
//        long daysBorrowed = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//
//        // Fine calculation (if more than 14 days)
//        double fine = 0;
//        if (daysBorrowed > 14) {
//            fine = (daysBorrowed - 14) * 5;  // ₹5 per extra day
//        }
//
//        borrowedBook.setFine(fine);
//        borrowedBookRepository.save(borrowedBook);
//
//        return fine > 0 ? "Book returned with a fine of ₹" + fine : "Book returned successfully!";
//    }
//
//}

// Another code service of java

import com.Lb.Entity.Book;
import com.Lb.Entity.BorrowedBook;
import com.Lb.Entity.User;
import com.Lb.Repo.BookRepository;
import com.Lb.Repo.BorrowedBookRepository;
import com.Lb.Repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

    }

    public String borrowBook(Long studentId, Long bookId) {
        Optional<User> user = userRepository.findById(studentId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isEmpty() || book.isEmpty()) {
            return "Student or Book not found!";
        }
        if (book.get().isBorrowed()) {
            return "Book already borrowed!";
        }

        book.get().setBorrowed(true);
        bookRepository.save(book.get());

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(user.get());
        borrowedBook.setBook(book.get());
        borrowedBookRepository.save(borrowedBook);

        return "Book borrowed successfully!";
    }

//    public String returnBook(Long borrowedBookId) {
//        Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findById(borrowedBookId);
//        if (borrowedBook.isEmpty()) {
//            return "Borrowed book not found!";
//        }
//
//        Book book = borrowedBook.get().getBook();
//        book.setBorrowed(false);
//        bookRepository.save(book);
////
////        Date returnDate = new Date();
////        borrowedBook.get().setReturnDate(returnDate);
//
//        // Fine Calculation (if returned late)
//        long daysLate = (returnDate.getTime() - borrowedBook.get().getBorrowedDate().getTime()) / (1000 * 60 * 60 * 24);
//        if (daysLate > 14) { // Assume due period is 14 days
//            borrowedBook.get().setFine((daysLate - 14) * 5.0);
//        }
//
//        borrowedBookRepository.save(borrowedBook.get());
//        return "Book returned successfully!";
//    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }





}