package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.FilterJwtConfig;
import com.railiac.rest.database.model.FilterRoleConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterJwtRoleRepository extends JpaRepository<FilterRoleConfig, Long> {

}