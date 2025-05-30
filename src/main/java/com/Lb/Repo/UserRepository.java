package com.Lb.Repo;



import com.Lb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByEmailOrPhone(String email, String phone);
}