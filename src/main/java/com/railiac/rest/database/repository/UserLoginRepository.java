package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.User;
import com.railiac.rest.database.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    List<UserLogin> findByUserAndEnabledIsTrue(User user);
    Optional<UserLogin> findByJwt(String jwt);
}