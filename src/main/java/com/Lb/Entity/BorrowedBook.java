package com.Lb.Entity;


//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Table(name = "borrowed_books")
//@Getter
//@Setter
//@AllArgsConstructor
//public class BorrowedBook {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "book_id", nullable = false)
//    private Book book;
//
//    @Temporal(TemporalType.DATE)
//    private Date borrowedDate;
//
//    @Temporal(TemporalType.DATE)
//    private Date returnDate;
//
//    private double fine;  // Fine amount for late return
//
//    public BorrowedBook(User user, Book book, Date borrowedDate, Date returnDate, double fine) {
//        this.user = user;
//        this.book = book;
//        this.borrowedDate = borrowedDate;
//        this.returnDate = returnDate;
//        this.fine = fine;
//    }
//    public BorrowedBook() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
//
//    public Date getBorrowedDate() {
//        return borrowedDate;
//    }
//
//    public void setBorrowedDate(Date borrowedDate)  {
//        this.borrowedDate = borrowedDate;
//    }
//
//    public Date getReturnDate() {
//        return returnDate;
//    }
//
//    public void setReturnDate(Date returnDate) {
//        this.returnDate = returnDate;
//    }
//
//    public double getFine() {
//        return fine;
//    }
//
//    public void setFine(double fine) {
//        this.fine = fine;
//    }
//}


// New BorrowedBook Entity

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime borrowedDate;

    private Double fine;

    private boolean isReturned = false;

    public BorrowedBook() {

        this.fine = 0.0;
    }

    public BorrowedBook(Long id, User user, Book book, LocalDateTime borrowedDate,  Double fine) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowedDate = borrowedDate;

        this.fine = fine;
    }

    private boolean returned = false;

    // ✅ Getter and Setter for 'returned' field
    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LocalDateTime getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDateTime borrowedDate) { this.borrowedDate = borrowedDate; }



    public Double getFine() { return fine; }
    public void setFine(Double fine) { this.fine = fine; }

    public void setReturnDate(Object o) {

    }
}