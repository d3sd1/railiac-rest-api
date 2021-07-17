package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.FilterJwtConfig;
import com.railiac.rest.database.model.PointTransaction;
import com.railiac.rest.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    List<PointTransaction> findAllByUser(User user);
}