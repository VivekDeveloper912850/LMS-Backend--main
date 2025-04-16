package com.Lb.Repo;

import com.Lb.Entity.ReturnedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnedBookRepository extends JpaRepository<ReturnedBook, Long> {
    List<ReturnedBook> findByUserId(Long id);
}