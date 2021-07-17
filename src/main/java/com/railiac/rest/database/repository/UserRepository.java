package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailCode(String emailCode);
    Optional<User> findBySelectorAndValidator(String selector, String validator);
    Optional<User> findByReferrerCode(String referrerCode);
}