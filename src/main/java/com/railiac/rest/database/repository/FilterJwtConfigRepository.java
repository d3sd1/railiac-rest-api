package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.FilterJwtConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilterJwtConfigRepository extends JpaRepository<FilterJwtConfig, Long> {

}